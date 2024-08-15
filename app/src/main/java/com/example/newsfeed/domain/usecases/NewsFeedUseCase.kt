package com.example.newsfeed.domain.usecases

import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.repository.NewsRepository
import com.example.newsfeed.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(offset: Int): Flow<State<Feed>> = flow {
        try {
            emit(State.Loading())
            val feed = repository.getAllNews(offset)
            emit(State.Success(feed))
        } catch (e: HttpException) {
            emit(State.Error(e.printStackTrace().toString()))
        } catch (e: IOException) {
            emit(State.Error(e.printStackTrace().toString()))
        }
    }
}