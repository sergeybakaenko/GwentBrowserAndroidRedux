package com.sigmasoftware.gwentreduxandroid.states.model

data class CardListResponse(val count: Int = 0, val next: String? = null, val results: List<CardLink> = ArrayList())