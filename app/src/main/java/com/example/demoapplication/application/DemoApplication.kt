package com.example.demoapplication.application

import android.app.Application
import android.content.Context
import com.example.demoapplication.BuildConfig
import com.example.demoapplication.di.appModules
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
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
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@DemoApplication)
            modules(appModules)
        }


        /** Firebase crashlytics , Analytics init goes here  */


    }

}