package com.example.demoapplication.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class GalleryImage(

    /**
     * {
    "id":"1",
    "author":"Alejandro Escamilla",
    "width":5616,
    "height":3744,
    "url":"https://unsplash.com/photos/LNRyGwIJr5c",
    "download_url":"https://picsum.photos/id/1/5616/3744"
    },
     */

    val id: Int,
    val author: String?,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url") val downloadUrl: String

) : Parcelable {
    override fun equals(other: Any?): Boolean {
        when (other) {
            is GalleryImage -> this.id == other.id
        }
        return false
    }
}