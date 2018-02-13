package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.actions.BeginCardsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FailedCardsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FinishCardsLoading
import com.sigmasoftware.gwentreduxandroid.states.model.CardListResponse

sealed class CardListState {

    class None: CardListState()
    class Loading: CardListState()
    data class Loaded(val cardListResponse: CardListResponse): CardListState()
    data class FailedLoading(val errorMessage: String = "Oops, something went wrong"): CardListState()

    fun reduce(action: Action): CardListState {
        return when(action) {
            is BeginCardsLoading -> Loading()
            is FinishCardsLoading -> Loaded(action.cardListResponse)
            is FailedCardsLoading -> FailedLoading(action.errorMessage)
            else -> {
                None()
            }
        }
    }
}