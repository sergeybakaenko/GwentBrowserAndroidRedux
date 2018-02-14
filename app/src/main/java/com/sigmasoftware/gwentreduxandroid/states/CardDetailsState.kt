package com.sigmasoftware.gwentreduxandroid.states

import com.sigmasoftware.gwentreduxandroid.actions.Action
import com.sigmasoftware.gwentreduxandroid.actions.FinishCardDetailsLoading
import com.sigmasoftware.gwentreduxandroid.states.model.CardDetailsResponse

data class CardDetailsState(val cardDetailsResponse: CardDetailsResponse = CardDetailsResponse()) {

    fun reduce(action: Action): CardDetailsState {
        return when (action) {
            is FinishCardDetailsLoading -> CardDetailsState(action.cardDetailsResponse)
            else -> {
                this
            }
        }
    }
}