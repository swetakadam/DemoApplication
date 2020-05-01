package com.example.demoapplication.di

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import android.os.LocaleList
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.demoapplication.BuildConfig
import com.example.demoapplication.data.remote.GalleryAPI
import com.example.demoapplication.data.remote.TokenInterceptor
import com.example.demoapplication.data.repository.GalleryRepository
import com.example.demoapplication.data.repository.GalleryRepositoryImpl
import com.example.demoapplication.ui.gallery.GalleryViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit



val appModules = module {

    //the retrofit service using our custom HTTP client instance as a singleton

    single {

        createWebService<GalleryAPI>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = com.example.demoapplication.BuildConfig.API_BASE_URL
        )

    }


    single {
        getTokenInterceptor()
    }

    single {
        getSecuredSharedPrefs(androidApplication())
    }

    single<SharedPreferences.Editor> {
        getSecuredSharedPrefs(androidApplication()).edit()
    }


    /* single<SharedPreferences> (named("securedPrefs")) {
         getSecuredSharedPrefs(androidApplication())
     }*/


    //Tells koin how to create an instance of Repositories
    factory<GalleryRepository> {
       GalleryRepositoryImpl(
            galleryApi = get()
        )
    }


    //specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel { GalleryViewModel(galleryRepository = get()) }

}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        "demo_application",
        android.content.Context.MODE_PRIVATE
    )
}
/** this API works only on devices running API > = 23 */
fun getSecuredSharedPrefs(androidApplication: Application): SharedPreferences {

    //(1) create or retrieve masterkey from Android keystore
    //masterkey is used to encrypt data encryption keys
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    //(2) Get instance of EncryptedSharedPreferences class
    // as part of the params we pass the storage name, reference to
    // masterKey, context and the encryption schemes used to
    // encrypt SharedPreferences keys and values respectively.
    val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        "demo_application_encrypted_prefs",
        masterKeyAlias,
        androidApplication,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    return encryptedSharedPreferences

}

fun getTokenInterceptor(): TokenInterceptor {
    return TokenInterceptor()
}



/* returns a custom okhttpclient instance with interceptor. Used for building retrofit */
fun createHttpClient(): OkHttpClient {

    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method, original.body).build()
        return@addInterceptor it.proceed(request)
    }


    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logging)
        client.addNetworkInterceptor(StethoInterceptor())
    }

    client.addInterceptor(AcceptLanguageHeaderInterceptor())

    return client.build()

}

/*
Okay HTTP with auth token
 */

fun createHTTPClientWithAuth(tokenInterceptor: TokenInterceptor): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method, original.body).build()
        return@addInterceptor it.proceed(request)
    }

    // Logging
    if (BuildConfig.DEBUG) {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logging)
        client.addNetworkInterceptor(StethoInterceptor())
    }
    //Token interceptor
    client.addInterceptor(tokenInterceptor)
    client.addInterceptor(AcceptLanguageHeaderInterceptor())

    return client.build()
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient, factory: CallAdapter.Factory, baseUrl: String
): T {

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

class AcceptLanguageHeaderInterceptor : Interceptor {

    private val language: String
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault().toLanguageTags()
        } else {
            Locale.getDefault().getLanguage()
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithHeaders = originalRequest.newBuilder()
            .header("Accept-Language", language)
            .build()
        return chain.proceed(requestWithHeaders)
    }
}