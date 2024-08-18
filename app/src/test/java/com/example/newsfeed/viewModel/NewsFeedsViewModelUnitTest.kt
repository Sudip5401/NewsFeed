package com.example.newsfeed.viewModel

import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.usecases.NewsFeedUseCase
import com.example.newsfeed.presentation.viewModel.NewsFeedsViewModel
import com.example.newsfeed.core.State
import com.example.newsfeed.utils.TestingDispatcher
import com.example.newsfeed.core.UiText
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NewsFeedsViewModelUnitTest {

    @MockK
    private lateinit var newsFeedUseCase: NewsFeedUseCase
    private lateinit var newsFeedsViewModel: NewsFeedsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val testDispatcher = TestingDispatcher()
        newsFeedsViewModel = NewsFeedsViewModel(newsFeedUseCase, testDispatcher)
    }

    @Test
    fun `when use case returns success then state should be success`() {
        runTest {
            coEvery { newsFeedUseCase(1) } returns flow { emit(State.Success(Feed())) }

            newsFeedsViewModel.fetchFeeds(1)
            coVerify(exactly = 1) { newsFeedUseCase(1) }

            assert(newsFeedsViewModel.newsFeeds.value is State.Success)
            assert(newsFeedsViewModel.newsFeeds.value.data != null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns error then state should be error`() {
        runBlockingTest {
            coEvery { newsFeedUseCase(1) } returns flow { emit(State.Error(UiText.unknownError())) }

            newsFeedsViewModel.fetchFeeds(1)
            coVerify(exactly = 1) { newsFeedUseCase(1) }

            assert(newsFeedsViewModel.newsFeeds.value is State.Error)
            assert(newsFeedsViewModel.newsFeeds.value.message != null)
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}