package com.sigmasoftware.gwentreduxandroid.screen.cardList

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sigmasoftware.gwentreduxandroid.Store
import com.sigmasoftware.gwentreduxandroid.actions.loadCardDetails
import com.sigmasoftware.gwentreduxandroid.actions.loadCards
import com.sigmasoftware.gwentreduxandroid.screen.cardDetails.CardDetailsActivity
import com.sigmasoftware.gwentreduxandroid.states.CardDetailsLoadingState
import com.sigmasoftware.gwentreduxandroid.states.CardListLoadingState
import com.sigmasoftware.gwentreduxandroid.states.State
import com.sigmasoftware.gwentreduxandroid.states.model.CardListResponse
import org.jetbrains.anko.startActivity

const val cardsListUrl = "https://api.gwentapi.com/v0/cards"

class CardListActivity : AppCompatActivity() {

    private lateinit var rootView: CardsListView

    private lateinit var removeListener: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = CardsListView(context = applicationContext)
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
            state.cardListLoadingState is CardListLoadingState.None ->
                rootView.props = CardsListView.Props(arrayListOf(), { loadCards(cardsListUrl) })
            state.cardListLoadingState is CardListLoadingState.Loading ->
                rootView.props = CardsListView.Props(arrayListOf(), null)
            state.cardListLoadingState is CardListLoadingState.Loaded ->
                rootView.props = CardsListView.Props(convertCardLinkToCard(state.cardListState.cardListResponse), { loadCards(cardsListUrl) })
            state.cardListLoadingState is CardListLoadingState.FailedLoading ->
                rootView.props = CardsListView.Props(convertCardLinkToCard(state.cardListState.cardListResponse), { loadCards(cardsListUrl) }, state.cardListLoadingState.errorMessage)
            state.cardDetailsLoadingState is CardDetailsLoadingState.Loading ->
                startActivity<CardDetailsActivity>()
        }
    }

    private fun convertCardLinkToCard(cardListResponse: CardListResponse): ArrayList<CardsListView.Props.Card> {
        return ArrayList<CardsListView.Props.Card>().apply {
            cardListResponse.results.forEach { add(CardsListView.Props.Card(it.name, { loadCardDetails(it.href) })) }
        }
    }
}
