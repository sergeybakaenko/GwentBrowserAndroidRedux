package com.sigmasoftware.gwentreduxandroid.actions

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.sigmasoftware.gwentreduxandroid.Store
import com.sigmasoftware.gwentreduxandroid.states.model.CardListResponse

fun loadCards() {
    Store.dispatch(action = BeginCardsLoading())

    "https://api.gwentapi.com/v0/cards".httpGet().responseString { _, response, result ->
        when (result) {
            is Result.Failure -> {
                Store.dispatch(action = FailedCardsLoading(result.error.localizedMessage))
            }
            is Result.Success -> {
                Klaxon().parse<CardListResponse>(result.value)?.let { Store.dispatch(action = FinishCardsLoading(it)) }
            }
        }
    }

}