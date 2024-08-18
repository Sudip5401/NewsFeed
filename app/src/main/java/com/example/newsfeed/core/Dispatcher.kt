package com.example.newsfeed.core

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {

    /** Dispatch work on Android's Main Thread **/
    val main: CoroutineDispatcher

    /** Dispatch work on a background thread for Networking **/
    val io: CoroutineDispatcher

}
