package com.example.newsfeed.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.core.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val dispatcher: Dispatcher) : ViewModel() {

    fun launchOnMain(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.main, coroutine)

    fun launchOnIO(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.io, coroutine)

    private fun launchOnViewModelScope(
        coroutineDispatcher: CoroutineDispatcher,
        coroutine: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineDispatcher, block = coroutine)
}