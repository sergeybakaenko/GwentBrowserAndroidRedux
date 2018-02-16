package com.sigmasoftware.gwentreduxandroid.states.model

data class CardDetailsResponse(
        val name: String = String(),
        val faction: CardFaction = CardFaction(),
        val flavor: String = String())