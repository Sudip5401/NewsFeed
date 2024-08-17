package com.example.newsfeed

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.repository.NewsRepository
import com.example.newsfeed.domain.usecases.NewsFeedUseCase
import com.example.newsfeed.presentation.NewsFeedState
import com.example.newsfeed.presentation.viewModel.NewsFeedsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class NewsFeedsUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Mock
    private lateinit var useCase: NewsFeedUseCase

    @Mock
    private lateinit var feedObject: Feed

    private lateinit var newsFeedsViewModel: NewsFeedsViewModel

    private lateinit var testDispatchers: TestDispatchers

    @Before
    fun setup() {
        openMocks(this)
        testDispatchers = TestDispatchers()
        newsFeedsViewModel = NewsFeedsViewModel(useCase)
    }

    @Test
    fun `check fetch all news is empty`(): Unit = runBlocking {
        /* When */
        `when`(useCase.invoke(anyInt())).then {
            return@then getMockedList()
        }

        /* Then */
        useCase.invoke(0)
        newsFeedsViewModel._newsFeeds.test {
            assertEquals(NewsFeedState(feeds = null), awaitItem())
        }
    }

    private fun getMockedList(): Flow<NewsFeedState> = flowOf(
        NewsFeedState(
            feeds = feedObject
        )
    )


    @After
    fun tearDown() {
        Mockito.reset(newsRepository)
    }
}

