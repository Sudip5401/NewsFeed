package com.example.newsfeed.domain.repository

import com.example.newsfeed.data.dataSource.dto.Feed

interface NewsRepository {
    suspend fun getAllNews(offset: Int): Feed
}