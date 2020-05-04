package com.example.demoapplication.ui.gallery

import android.os.Parcelable
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.demoapplication.data.model.GalleryImage
import com.example.demoapplication.data.paging.GalleryDataSource
import com.example.demoapplication.data.paging.GalleryDataSourceFactory
import com.example.demoapplication.data.repository.GalleryRepository
import timber.log.Timber

class GalleryViewModel(private val handle: SavedStateHandle, private val galleryRepository: GalleryRepository) : ViewModel() {

    private lateinit var galleryDataSource: GalleryDataSourceFactory
    //Observables
    lateinit var imagesLiveData: LiveData<PagedList<GalleryImage>>

    companion object {
        private const val LIST_STATE = "list"
        private const val LIST_POSITION = "position"
    }

    var listState: Parcelable?
        get() = handle.get(LIST_STATE)
        set(value) {
            handle.set(LIST_STATE, value)
        }

    var listPositions: IntArray?
        get() = handle.get(LIST_POSITION)
        set(value) {
            handle.set(LIST_POSITION, value)
        }
    


    fun getGalleryStatusLiveData(): LiveData<Boolean> {
        return galleryRepository.getGalleryEmptyStatusLiveData()
    }

    fun getGalleryImages() {

        if(listState != null && imagesLiveData.value != null ) {
            imagesLiveData = imagesLiveData
            return
        }

       galleryDataSource =
            GalleryDataSourceFactory(
                galleryRepository,
                scope = this.viewModelScope
            )

        imagesLiveData =
            LivePagedListBuilder(galleryDataSource, pagedListConfig()).build()


    }


    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(2)
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()


    override fun onCleared() {
        super.onCleared()
        Timber.d("on cleared called ")
    }
}