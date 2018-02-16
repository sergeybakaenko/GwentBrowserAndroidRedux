package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.actions.BeginCardsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FailedCardsLoading
import com.sigmasoftware.gwentreduxandroid.actions.FinishCardsLoading

sealed class CardListLoadingState {

    data class NextUrlAvailable(val nextUrl: String? = null) : CardListLoadingState()
    class Loading : CardListLoadingState()
    data class Failed(val errorMessage: String = "Oops, something went wrong") : CardListLoadingState()

    fun reduce(action: Action): CardListLoadingState {
        return when (action) {
            is BeginCardsLoading -> Loading()
            is FinishCardsLoading -> NextUrlAvailable(action.cardListResponse.next)
            is FailedCardsLoading -> Failed(action.errorMessage)
            else -> {
                this
            }
        }
    }
}