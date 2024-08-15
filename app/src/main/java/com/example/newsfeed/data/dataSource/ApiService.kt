package com.example.newsfeed.data.dataSource

import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/news")
    suspend fun getAllNews(
        @Query("access_key") accessKey: String = Constants.ACCESS_KEY,
        @Query("offset") offset: String
    ): Feed
}