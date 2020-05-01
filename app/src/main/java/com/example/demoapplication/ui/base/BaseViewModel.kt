package com.example.demoapplication.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    //ToDO Should be replaced by
    //  androidx.lifecycle:lifecycle-viewmodel-ktx:$version
    // The ViewModel KTX library provides a viewModelScope() function that makes it easier to launch coroutines from your ViewModel. The CoroutineScope is bound to Dispatchers.Main and is automatically cancelled when the ViewModel is cleared. You can use viewModelScope() instead of creating a new scope for each ViewModel.
    // https://developer.android.com/kotlin/ktx
    /**
     * This is a scope for all coroutines launched by [BaseViewModel]
     * that will be dispatched in Main Thread
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main)

    /**
     * This is a scope for all coroutines launched by [BaseViewModel]
     * that will be dispatched in a Pool of Thread
     */
    protected val ioScope = CoroutineScope(Dispatchers.IO)

    protected val defaultScope = CoroutineScope(Dispatchers.Default)

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        uiScope.coroutineContext.cancel()
        ioScope.coroutineContext.cancel()
        defaultScope.coroutineContext.cancel()
    }
}