package com.mvi.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

abstract class EffectMVI<State: Any, Event: Any, Effect: Any> (
    initialState: State,
    coroutineScope: CoroutineScope,
    bootstrapper: List<Event> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): EffectNewsMVI<State, Event, Effect, Nothing>(initialState, coroutineScope, bootstrapper, dispatcher)