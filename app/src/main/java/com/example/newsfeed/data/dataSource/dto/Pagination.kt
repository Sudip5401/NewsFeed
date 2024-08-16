package com.example.newsfeed.data.dataSource.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pagination(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?
) : Parcelable