package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action

data class State(val cardListLoadingState: CardListLoadingState = CardListLoadingState.None(), val cardListState: CardListState = CardListState()) {

    fun reduce(action: Action): State {
        return State(cardListLoadingState.reduce(action))
    }
}