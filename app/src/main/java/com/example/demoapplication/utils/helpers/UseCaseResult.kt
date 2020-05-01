package com.example.demoapplication.utils.helpers

sealed class UseCaseResult<out T : Any> {

    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
    class PagedSuccess<out T : Any>(val data: T, val isLastPage: Boolean) : UseCaseResult<T>()

}