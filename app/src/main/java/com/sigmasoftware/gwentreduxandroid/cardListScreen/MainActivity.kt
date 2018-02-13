package com.sigmasoftware.gwentreduxandroid.cardListScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sigmasoftware.gwentreduxandroid.Store
import com.sigmasoftware.gwentreduxandroid.actions.loadCards
import com.sigmasoftware.gwentreduxandroid.cardListScreen.views.CardsListView
import com.sigmasoftware.gwentreduxandroid.states.CardListState
import com.sigmasoftware.gwentreduxandroid.states.State
import com.sigmasoftware.gwentreduxandroid.states.model.CardListResponse

class MainActivity : AppCompatActivity() {
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
            state.cardListState is CardListState.None ->
                rootView.props = CardsListView.Props(arrayListOf(), { loadCards() })
            state.cardListState is CardListState.Loading ->
                rootView.props = CardsListView.Props(arrayListOf(), null)
            state.cardListState is CardListState.Loaded ->
                rootView.props = CardsListView.Props(convertCardLinkToCard(state.cardListState.cardListResponse), { loadCards() })
            state.cardListState is CardListState.FailedLoading -> {
                rootView.props = CardsListView.Props(arrayListOf(), { loadCards() }, state.cardListState.errorMessage)
            }
        }
    }

    private fun convertCardLinkToCard(cardListResponse: CardListResponse): ArrayList<CardsListView.Props.Card> {
        return ArrayList<CardsListView.Props.Card>().apply {
            cardListResponse.results.forEach { add(CardsListView.Props.Card(it.name)) }
        }
    }
}