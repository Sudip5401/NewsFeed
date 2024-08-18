package com.example.newsfeed.data.repository

import com.example.newsfeed.R
import com.example.newsfeed.core.State
import com.example.newsfeed.core.UiText
import com.example.newsfeed.data.dataSource.ApiService
import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImplementation @Inject constructor(private val api: ApiService) :
    NewsRepository {
    override suspend fun getAllNews(offset: Int): Flow<State<Feed>> = flow {
        try {
            emit(State.Loading())
            emit(State.Success(api.getAllNews(offset = offset.toString())))
        } catch (e: HttpException) {
            emit(
                State.Error(
                    message =
                    UiText.StringResource(R.string.error_unable_to_reach_server)
                )
            )
        } catch (e: IOException) {
            println("Exception : ${e.message}")
            emit(State.Error(UiText.StringResource(R.string.something_went_wrong)))
        }
    }
}
