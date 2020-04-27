package com.example.demoapplication.application

import android.app.Application
import android.content.Context
import com.example.demoapplication.BuildConfig
import timber.log.Timber


class DemoApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: DemoApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }


    override fun onCreate() {
        super.onCreate()



        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


    }

}