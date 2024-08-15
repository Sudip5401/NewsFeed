package com.example.newsfeed.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.usecases.NewsFeedUseCase
import com.example.newsfeed.presentation.NewsFeedState
import com.example.newsfeed.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedsViewModel @Inject constructor(
    private val newsFeedsUseCase: NewsFeedUseCase
) : ViewModel() {

    private val newsFeeds = MutableStateFlow(NewsFeedState())
    var _newsFeeds: StateFlow<NewsFeedState> = newsFeeds

    fun fetchFeeds(offSet: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsFeedsUseCase.invoke(offSet).collect {
            when (it) {
                is State.Loading -> {
                    newsFeeds.value = NewsFeedState(isLoading = true)
                    Log.d("on Loading", _newsFeeds.value.isLoading.toString())
                }

                is State.Success -> {
                    newsFeeds.value = NewsFeedState(feeds = it.data)
                    Log.d("on Success", _newsFeeds.value.feeds?.pagination?.count.toString())
                }

                is State.Error -> {
                    newsFeeds.value = NewsFeedState(error = it.message ?: "An Unexpected Error")
                    Log.d("on Error", it.message.toString())
                }
            }
        }
    }

}