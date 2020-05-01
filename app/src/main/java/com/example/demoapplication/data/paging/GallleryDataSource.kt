package com.example.demoapplication.data.paging

import androidx.paging.PageKeyedDataSource
import com.example.demoapplication.data.model.GalleryImage
import com.example.demoapplication.data.repository.GalleryRepository
import com.example.demoapplication.utils.helpers.UseCaseResult
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Created by swetashinde on 30,April,2020
 * USA
 */

const val PAGE_SIZE = 10
const val PAGE_FIRST = 1

class GalleryDataSource(
    private val repository: GalleryRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, GalleryImage>() {


    private var supervisorJob = SupervisorJob()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GalleryImage>
    ) {

        scope.launch(getJobErrorHandler() + supervisorJob) {

            when (val result =
                repository.getGalleryWithPagination(
                    PAGE_FIRST,
                    PAGE_SIZE
                )) {
                is UseCaseResult.PagedSuccess -> {
                    val images = result.data
                    val nextPage = if (result.isLastPage) null else 2
                    Timber.d("load initial success called  and next page is $nextPage")

                    callback.onResult(images, null, nextPage)
                }
                is UseCaseResult.Error -> {
                    Timber.d("Failed to fetch data load initial ")
                }
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryImage>) {

        val page = params.key
        scope.launch(getJobErrorHandler() + supervisorJob) {
            Timber.d("load after called page number is $page")


            when (val result =
                repository.getGalleryWithPagination(
                    page,
                    PAGE_SIZE
                )) {
                is UseCaseResult.PagedSuccess -> {
                    val images = result.data
                    val nextPage = if (result.isLastPage) null else page + 1
                    //need to stop the call backs if last page is reached
                    Timber.d("load after success called ")
                    callback.onResult(images, nextPage)
                }
                is UseCaseResult.Error -> {
                    Timber.d("Failed to fetch data load after ")
                }
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryImage>) {

    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }


    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.d("An error happened: $e")
    }
}