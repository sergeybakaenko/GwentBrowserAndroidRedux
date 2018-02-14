package com.sigmasoftware.gwentreduxandroid.screen.cardDetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sigmasoftware.gwentreduxandroid.Store
import com.sigmasoftware.gwentreduxandroid.states.CardDetailsLoadingState
import com.sigmasoftware.gwentreduxandroid.states.State

class CardDetailsActivity : AppCompatActivity() {
    private lateinit var removeListener: () -> Unit
    private lateinit var rootView: CardDetailsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = CardDetailsView(context = applicationContext)
        setContentView(rootView)
    }

    override fun onPause() {
        removeListener()
        super.onPause()
    }

    override fun onResume() {
        removeListener = Store.addListener { state ->
            runOnUiThread {
                this.onNewState(state)
            }
        }
        super.onResume()
    }

    private fun onNewState(state: State) {
        when {
            state.cardDetailsLoadingState is CardDetailsLoadingState.Loading ->
                rootView.props = CardDetailsView.Props(cardLoading = true)
            state.cardDetailsLoadingState is CardDetailsLoadingState.Loaded ->
                rootView.props = CardDetailsView.Props(
                        state.cardDetailsState.cardDetailsResponse.categories[0].name,
                        state.cardDetailsState.cardDetailsResponse.cardFaction.name,
                        state.cardDetailsState.cardDetailsResponse.cardFlavor)
        }
    }
}