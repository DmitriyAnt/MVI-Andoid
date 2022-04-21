package com.mvi.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

abstract class SimpleReducerMVI<State: Any, Event: Any> (
    initialState: State,
    coroutineScope: CoroutineScope,
    bootstrapper: List<Event> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {

    private val events = Channel<Event>(Channel.BUFFERED)

    val state = events.receiveAsFlow()
        .runningFold(initialState, ::reduceState)
        .flowOn(dispatcher)
        .stateIn(coroutineScope, Eagerly, initialState)

    fun sendEvent(event: Event) {
        events.trySend(event)
    }

    protected abstract suspend fun reduceState(currentState: State, event: Event): State


    init {
        if (bootstrapper.isNotEmpty()) bootstrapper.forEach { sendEvent(it) }
    }
}