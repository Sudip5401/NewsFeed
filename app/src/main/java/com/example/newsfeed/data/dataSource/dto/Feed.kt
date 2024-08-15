package com.example.newsfeed.data.dataSource.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("data") val data: List<Data>,
    @SerializedName("pagination") val pagination: Pagination
) {
    override fun toString(): String {
        return "Api Response : ${Gson().toJson(this)}"
    }
}