package com.mvi.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

abstract class EffectNewsMVI<State: Any, Event: Any, Effect: Any, News: Any> (
    initialState: State,
    coroutineScope: CoroutineScope,
    bootstrapper: List<Event> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): BaseMVI<State, Event, Effect, News>(initialState, coroutineScope, bootstrapper, dispatcher){

    final override fun postProcessor(effect: Effect): Event? = null

}