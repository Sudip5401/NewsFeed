package com.example.newsfeed.domain.repository

import com.example.newsfeed.core.State
import com.example.newsfeed.data.dataSource.dto.Feed
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getAllNews(offset: Int): Flow<State<Feed>>
}