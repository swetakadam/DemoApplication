package com.example.demoapplication.data.remote

import com.example.demoapplication.data.model.GalleryImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by swetashinde on 30,April,2020
 * USA
 */

interface GalleryAPI {

    @GET("v2/list")
    suspend fun getGalleryWithPagination(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<GalleryImage>>

}