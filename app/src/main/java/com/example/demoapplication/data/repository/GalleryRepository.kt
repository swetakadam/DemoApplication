package com.example.demoapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.demoapplication.data.model.GalleryImage
import com.example.demoapplication.data.remote.GalleryAPI
import com.example.demoapplication.utils.helpers.UseCaseResult
import timber.log.Timber


interface GalleryRepository {


    suspend fun getGalleryWithPagination(page: Int, limit: Int): UseCaseResult<List<GalleryImage>>
    fun getGalleryEmptyStatusLiveData(): MutableLiveData<Boolean>


}

class GalleryRepositoryImpl(private val galleryApi: GalleryAPI) : GalleryRepository {

    private var noGalleryData: MutableLiveData<Boolean> = MutableLiveData()

    override fun getGalleryEmptyStatusLiveData(): MutableLiveData<Boolean> {
        return noGalleryData
    }

    override suspend fun getGalleryWithPagination(
        pageNumber: Int,
        pageSize: Int
    ): UseCaseResult<List<GalleryImage>> {

        return try {
            val resultResponse = galleryApi.getGalleryWithPagination(pageNumber, pageSize)
            if (resultResponse.isSuccessful) {
                val result = resultResponse.body()

                /** this api does not have total records, we are going to hard limit to 200 images
                 * we need total records to make pagination library stop fetching pages when last page is reached*/
                val totalRecords = 50
                val isLastPage = (pageNumber * pageSize > totalRecords ?: 0)

                //typically it should come from API
                if (totalRecords == 0) {
                    Timber.d("total records is zero")
                    noGalleryData.postValue(true) // needed to show empty view for recyclerview
                }
                UseCaseResult.PagedSuccess(result ?: emptyList(), isLastPage)
            } else {
                //Handle unsuccessful response
                val ex = java.lang.Exception(resultResponse.message())
                UseCaseResult.Error(ex)
            }
        } catch (ex: Exception) {
            Timber.d("total records is zero exception ${ex.message}")

            UseCaseResult.Error(ex)
        }

    }

}