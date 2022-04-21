package com.mvi.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class EffectNewsMVIViewModel<State: Any, Event: Any, Effect: Any, News: Any> (
    initialState: State,
    bootstrapper: List<Event> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): BaseMVIViewModel<State, Event, Effect, News>(initialState, bootstrapper, dispatcher){

    final override fun postProcessor(effect: Effect): Event? = null

}