package com.ananth.artbooktesting.model

import com.google.gson.annotations.SerializedName

data class ImageResult(
    val comments: Int,
    val downloads: Int,
    val favorites: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val largeImageUrl: String,
    val likes: Int,
    @SerializedName("pageURL")
    val pageUrl: String,
    val previewHeight: Int,
    @SerializedName("previewURL")
    val previewUrl: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("userImageURL")
    val userImageUrl: String,
    val view: Int,
    val webFormatHeight: Int,
    val webFormatUrl: String,
    val webFormatWidth: Int

)
