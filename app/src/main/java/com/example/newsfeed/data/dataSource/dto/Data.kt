package com.example.newsfeed.data.dataSource.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val author: String? = null,
    val category: String? = null,
    val country: String? = null,
    val description: String? = null,
    val image: String? = null,
    val language: String? = null,
    @SerializedName("published_at") val publishedAt: String? = null,
    val source: String? = null,
    val title: String? = null,
    val url: String? = null
) : Parcelable