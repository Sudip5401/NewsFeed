package com.example.newsfeed.domain.usecases

import com.example.newsfeed.core.State
import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(offset: Int): Flow<State<Feed>> = repository.getAllNews(offset)
}