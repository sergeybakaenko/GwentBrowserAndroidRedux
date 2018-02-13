package com.sigmasoftware.gwentreduxandroid.actions

import com.sigmasoftware.gwentreduxandroid.states.model.CardListResponse

data class FinishCardsLoading(val cardListResponse: CardListResponse) : Action