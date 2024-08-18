package com.example.newsfeed.core

import kotlinx.coroutines.Dispatchers

class AppDispatcher : Dispatcher {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}
