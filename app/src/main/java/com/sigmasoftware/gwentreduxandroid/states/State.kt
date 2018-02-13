package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action

data class State(val cardListState: CardListState = CardListState.None()) {

    fun reduce(action: Action): State {
        return State(cardListState.reduce(action))
    }
}