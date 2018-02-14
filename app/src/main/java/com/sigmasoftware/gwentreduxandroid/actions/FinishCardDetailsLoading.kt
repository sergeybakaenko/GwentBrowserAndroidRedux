package com.sigmasoftware.gwentreduxandroid.actions

import com.sigmasoftware.gwentreduxandroid.states.model.CardDetailsResponse

data class FinishCardDetailsLoading(val cardDetailsResponse: CardDetailsResponse = CardDetailsResponse()) : Action