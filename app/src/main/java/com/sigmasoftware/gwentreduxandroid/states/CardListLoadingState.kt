package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.actions.BeginCardsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FailedCardsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FinishCardsLoading

sealed class CardListLoadingState {

    class None: CardListLoadingState()
    class Loading: CardListLoadingState()
    class Loaded: CardListLoadingState()
    data class FailedLoading(val errorMessage: String = "Oops, something went wrong"): CardListLoadingState()

    fun reduce(action: Action): CardListLoadingState {
        return when(action) {
            is BeginCardsLoading -> Loading()
            is FinishCardsLoading -> Loaded()
            is FailedCardsLoading -> FailedLoading(action.errorMessage)
            else -> {
                this
            }
        }
    }
}