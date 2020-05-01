package com.example.demoapplication.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.demoapplication.data.model.GalleryImage
import com.example.demoapplication.data.paging.GalleryDataSource
import com.example.demoapplication.data.paging.GalleryDataSourceFactory
import com.example.demoapplication.data.repository.GalleryRepository
import timber.log.Timber

class GalleryViewModel(private val galleryRepository: GalleryRepository) : ViewModel() {

    private lateinit var galleryDataSource: GalleryDataSourceFactory
    //Observables
    lateinit var imagesLiveData: LiveData<PagedList<GalleryImage>>


    fun getGalleryStatusLiveData(): LiveData<Boolean> {
        return galleryRepository.getGalleryEmptyStatusLiveData()
    }

    fun getGalleryImages() {

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




}