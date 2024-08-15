package com.example.newsfeed.data.repository

import com.example.newsfeed.data.dataSource.ApiService
import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImplementation @Inject constructor(private val api: ApiService) : NewsRepository {
    override suspend fun getAllNews(offset: Int): Feed {
        return api.getAllNews(offset = offset.toString())
    }
}