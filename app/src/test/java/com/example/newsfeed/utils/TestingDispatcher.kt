package com.example.newsfeed.utils

import com.example.newsfeed.core.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Unconfined

class TestingDispatcher : Dispatcher {
    override val main: CoroutineDispatcher = Unconfined
    override val io: CoroutineDispatcher = Unconfined
}
