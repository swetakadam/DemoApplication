package com.example.demoapplication.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.demoapplication.data.model.GalleryImage
import com.example.demoapplication.data.repository.GalleryRepository
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

/**
 * Created by swetashinde on 30,April,2020
 * USA
 */

class GalleryDataSourceFactory(
    private val repository: GalleryRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, GalleryImage>() {

    val source = MutableLiveData<GalleryDataSource>()

    override fun create(): DataSource<Int, GalleryImage> {
        Timber.d("On Create Called ")
        val source = GalleryDataSource(repository, scope = scope)
        this.source.postValue(source)
        return source
    }

    fun refresh() {
        source.value?.invalidate()

        val s = GalleryDataSource(
            repository,
            scope = scope
        )
        this.source.postValue(s)

    }


}