package com.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

abstract class SimpleReducerMVIViewModel<State: Any, Event: Any> (
    initialState: State,
    bootstrapper: List<Event> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): ViewModel() {

    private val events = Channel<Event>(Channel.BUFFERED)

    val state = events.receiveAsFlow()
        .runningFold(initialState, ::reduceState)
        .flowOn(dispatcher)
        .stateIn(viewModelScope, Eagerly, initialState)

    fun sendEvent(event: Event) {
        events.trySend(event)
    }

    protected abstract suspend fun reduceState(currentState: State, event: Event): State


    init {
        if (bootstrapper.isNotEmpty()) bootstrapper.forEach { sendEvent(it) }
    }
}