package com.sigmasoftware.gwentreduxandroid.states.model

data class CardDetailsResponse(val categories: List<CardLink> = ArrayList(),
                               val cardFaction: CardFaction = CardFaction(),
                               val cardFlavor: String = String())