package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.actions.FinishCardsLoading
import com.sigmasoftware.gwentreduxandroid.states.model.CardLink

data class CardListState(val cardList: List<CardLink> = ArrayList()) {

    fun reduce(action: Action): CardListState {
        return when (action) {
            is FinishCardsLoading -> CardListState(cardList + action.cardListResponse.results)
            else -> {
                this
            }
        }
    }
}