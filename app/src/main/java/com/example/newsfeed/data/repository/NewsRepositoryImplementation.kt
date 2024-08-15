package com.example.newsfeed.data.repository

import com.example.newsfeed.data.dataSource.ApiInterface
import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImplementation @Inject constructor(private val api: ApiInterface) : NewsRepository {
    override suspend fun getAllNews(offset: Int): Feed {
        return api.getAllNews(offset = offset.toString())
    }
}