package com.example.newsfeed.core

sealed class State<T>(val data: T? = null, val message: UiText? = null) {
    class Loading<T> : State<T>()
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(message: UiText, data: T? = null) : State<T>(data, message)
}