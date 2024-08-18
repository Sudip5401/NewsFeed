package com.example.newsfeed.usecase

import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.repository.NewsRepository
import com.example.newsfeed.domain.usecases.NewsFeedUseCase
import com.example.newsfeed.core.State
import com.example.newsfeed.core.UiText
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NewsFeedsUseCaseUnitTest {

    @MockK
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsFeedUseCase: NewsFeedUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsFeedUseCase = NewsFeedUseCase(newsRepository)
    }

    @Test
    fun `when repo is success State should return success`() {
        mockSuccess()

        runTest {
            val flow = newsFeedUseCase.invoke(0)

            val flowCount = flow.count()
            assert(flowCount == 2)

            val firstState = flow.first()
            assert(firstState is State.Loading)

            val lastState = flow.last()
            assert(lastState is State.Success)
            assert(lastState.data != null)
        }
    }

    @Test
    fun `when repo return error State should return error`() {
        mockError()

        runTest {
            val flow = newsFeedUseCase.invoke(0)

            val flowCount = flow.count()
            assert(flowCount == 2)

            val firstState = flow.first()
            assert(firstState is State.Loading)

            val lastState = flow.last()
            assert(lastState is State.Error)
            assert(lastState.message != null)
        }
    }

    private fun mockSuccess() {
        coEvery { newsRepository.getAllNews(any() as Int) } returns flow {
            emit(State.Loading())
            emit(State.Success(Feed()))
        }
    }

    private fun mockError() {
        coEvery { newsRepository.getAllNews(any()) } returns flow {
            emit(State.Loading())
            emit(State.Error(UiText.unknownError()))
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}