package com.mvi.mvicoroutine

import com.mvi.viewmodel.BaseMVIViewModel
import com.mvi.mvicoroutine.MainViewModel.*
import com.mvi.viewmodel.EffectNewsMVIViewModel
import kotlinx.coroutines.delay

class MainViewModel: BaseMVIViewModel<State, Event, Effect, Nothing>(
    initialState = State(),
    bootstrapper = arrayListOf(Event.Increment, Event.Increment)
) {
    data class State(val counter: Int = 0)

    sealed class Event{
        object Increment : Event()
        object Decrement : Event()
        object DecrementIncrement: Event()
    }

    sealed class Effect{
        object Increment : Effect()
        object Decrement : Effect()
        object DecrementIncrement: Effect()
    }


    override suspend fun reduceEffect(event: Event): Effect =
        when (event) {
            Event.Increment -> {
                delay(1000)
                Effect.Increment
            }
            Event.Decrement -> Effect.Decrement
            Event.DecrementIncrement -> Effect.DecrementIncrement
        }

    override suspend fun reduceState(currentState: State, effect: Effect): State =
        when (effect) {
            Effect.Increment -> currentState.copy(counter = currentState.counter + 1)
            Effect.Decrement -> currentState.copy(counter = currentState.counter - 1)
            Effect.DecrementIncrement -> currentState.copy(counter = currentState.counter - 1)
        }

    override fun postProcessor(effect: Effect): Event? =
        when(effect){
            Effect.DecrementIncrement -> Event.Increment
            Effect.Decrement -> null
            Effect.Increment -> null
        }
}