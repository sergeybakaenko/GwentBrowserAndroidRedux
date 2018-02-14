package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.actions.BeginCardDetailsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FailedCardDetailsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FinishCardDetailsLoading


sealed class CardDetailsLoadingState {
    class None : CardDetailsLoadingState()
    class Loading : CardDetailsLoadingState()
    class Loaded : CardDetailsLoadingState()
    data class FailedLoading(val errorMessage: String = "Oops, something went wrong") : CardDetailsLoadingState()

    fun reduce(action: Action): CardDetailsLoadingState {
        return when (action) {
            is BeginCardDetailsLoading -> Loading()
            is FinishCardDetailsLoading -> Loaded()
            is FailedCardDetailsLoading -> FailedLoading(action.errorMessage)
            else -> {
                this
            }
        }
    }
}