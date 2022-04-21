package com.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

abstract class BaseMVIViewModel<State: Any, Event: Any, Effect: Any, News: Any> (
    initialState: State,
    bootstrapper: List<Event> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): ViewModel(){

    private val events = Channel<Event>(Channel.BUFFERED)

    fun sendEvent(event: Event) {
        events.trySend(event)
    }

    private val newsChannel = Channel<News>(Channel.BUFFERED)

    private fun sendNews(news: News) {
        newsChannel.trySend(news)
    }

    val news = newsChannel.receiveAsFlow()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, Eagerly, null)

    val state: StateFlow<State> = events.receiveAsFlow()
        .map(::reduceEffect)
        .map(::postProcessorImp)
        .map(::reduceNewsImp)
        .runningFold(initialState, ::reduceState)
        .flowOn(dispatcher)
        .stateIn(viewModelScope, Eagerly, initialState)

    protected abstract suspend fun reduceEffect(event: Event): Effect

    protected abstract suspend fun reduceState(currentState: State, effect: Effect): State

    protected abstract fun postProcessor(effect: Effect): Event?

    protected fun reduceNews(effect: Effect): News? = null


    private fun postProcessorImp(effect: Effect): Effect{
        postProcessor(effect)?.let { sendEvent(it) }
        return effect
    }

    private fun reduceNewsImp(effect: Effect): Effect {
        reduceNews(effect)?.let { sendNews(it) }
        return effect
    }

    init {
        if (bootstrapper.isNotEmpty()) bootstrapper.forEach { sendEvent(it) }
    }
}