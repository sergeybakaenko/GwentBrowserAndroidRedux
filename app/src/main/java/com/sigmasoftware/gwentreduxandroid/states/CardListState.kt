package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.actions.FinishCardsLoading
import com.sigmasoftware.gwentreduxandroid.states.model.CardListResponse


data class CardListState(val cardListResponse: CardListResponse = CardListResponse()) {

    fun reduce(action: Action): CardListState {
        return when (action) {
            is FinishCardsLoading -> CardListState(action.cardListResponse)
            else -> {
                this
            }
        }
    }
}