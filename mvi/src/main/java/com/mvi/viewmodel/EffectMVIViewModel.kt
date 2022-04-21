package com.mvi.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class EffectMVIViewModel<State: Any, Event: Any, Effect: Any> (
    initialState: State,
    bootstrapper: List<Event> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): EffectNewsMVIViewModel<State, Event, Effect, Nothing>(initialState, bootstrapper, dispatcher)