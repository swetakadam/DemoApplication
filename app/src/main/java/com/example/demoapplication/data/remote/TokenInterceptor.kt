package com.example.demoapplication.data.remote

import okhttp3.Interceptor
import okhttp3.Response



class TokenInterceptor : Interceptor {

    var token: String = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder()
            .addHeader("access-token", token)
            .url(originalHttpUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)

    }

}