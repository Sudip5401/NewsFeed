package com.example.newsfeed.presentation.viewModel

import com.example.newsfeed.core.Dispatcher
import com.example.newsfeed.core.State
import com.example.newsfeed.data.dataSource.dto.Feed
import com.example.newsfeed.domain.usecases.NewsFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsFeedsViewModel @Inject constructor(
    private val newsFeedsUseCase: NewsFeedUseCase, dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

    internal var offsetValue = 0
    private val _newsFeeds = MutableStateFlow<State<Feed>>(State.Loading())
    val newsFeeds = _newsFeeds.asStateFlow()

    init {
        fetchFeeds(
            offsetValue
        )
    }

    fun fetchFeeds(offSet: Int) =
        launchOnIO {
            newsFeedsUseCase.invoke(offSet).collect {
                _newsFeeds.value = it
            }
        }
}