package com.sigmasoftware.gwentreduxandroid

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.states.State

object Store {

    var state: State = State()

    private val stateListeners: MutableList<(State) -> Unit> = ArrayList()

    fun dispatch(action: Action) {
        state = state.reduce(action)
        stateListeners.forEach { it(state) }
    }

    fun addListener(stateListener: (State) -> Unit): () -> Unit {
        stateListeners.add(stateListener)
        stateListener(state)
        return {
            this.stateListeners.remove(stateListener)
        }
    }
}