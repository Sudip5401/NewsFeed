package com.example.newsfeed.presentation

import com.example.newsfeed.data.dataSource.dto.Feed

data class NewsFeedState(
    val isLoading: Boolean = false,
    val feeds: Feed? = null,
    val error: String = ""
)