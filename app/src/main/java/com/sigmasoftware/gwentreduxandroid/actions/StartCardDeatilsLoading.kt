package com.sigmasoftware.gwentreduxandroid.actions

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.sigmasoftware.gwentreduxandroid.Store
import com.sigmasoftware.gwentreduxandroid.states.model.CardDetailsResponse


fun loadCardDetails(cardUrl: String) {
    Store.dispatch(BeginCardDetailsLoading())

    cardUrl.httpGet().responseString { _, _, result ->
        when (result) {
            is Result.Failure -> {
                Store.dispatch(action = FailedCardDetailsLoading(result.error.localizedMessage))
            }
            is Result.Success -> {
                Klaxon().parse<CardDetailsResponse>(result.value)?.let { Store.dispatch(action = FinishCardDetailsLoading(it)) }
            }
        }
    }
}