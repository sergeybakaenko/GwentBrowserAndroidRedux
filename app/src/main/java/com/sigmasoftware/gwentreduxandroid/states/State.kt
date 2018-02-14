package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action

data class State(val cardListLoadingState: CardListLoadingState = CardListLoadingState.None(),
                 val cardListState: CardListState = CardListState(),
                 val cardDetailsState: CardDetailsState = CardDetailsState(),
                 val cardDetailsLoadingState: CardDetailsLoadingState = CardDetailsLoadingState.None()) {

    fun reduce(action: Action): State {
        return State(cardListLoadingState.reduce(action),
                cardListState.reduce(action),
                cardDetailsState.reduce(action),
                cardDetailsLoadingState.reduce(action))
    }
}