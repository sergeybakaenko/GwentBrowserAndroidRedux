package com.sigmasoftware.gwentreduxandroid.screens.cardList

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sigmasoftware.gwentreduxandroid.Store
import com.sigmasoftware.gwentreduxandroid.actions.loadCardDetails
import com.sigmasoftware.gwentreduxandroid.actions.loadCards
import com.sigmasoftware.gwentreduxandroid.screens.cardDetails.CardDetailsActivity
import com.sigmasoftware.gwentreduxandroid.states.CardListLoadingState
import com.sigmasoftware.gwentreduxandroid.states.State
import com.sigmasoftware.gwentreduxandroid.states.model.CardLink
import org.jetbrains.anko.startActivity


class CardListActivity : AppCompatActivity() {

    private var cardsListUrl = "https://api.gwentapi.com/v0/cards"

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

        fun cardList(): ArrayList<CardsListView.Props.Card> {
            return convertCardLinkToCard(state.cardListState.cardList)
        }

        fun cardListLoad(): CardsListView.Props.ListLoad {
            return when {
                state.cardListLoadingState is CardListLoadingState.NextUrlAvailable ->
                    CardsListView.Props.ListLoad.Available({
                        state.cardListLoadingState.nextUrl?.let {
                            loadCards(it)
                        }
                    })
                state.cardListLoadingState is CardListLoadingState.Loading -> CardsListView.Props.ListLoad.Loading()
                state.cardListLoadingState is CardListLoadingState.Failed -> CardsListView.Props.ListLoad.Failed(state.cardListLoadingState.errorMessage)
                else -> {
                    CardsListView.Props.ListLoad.Available({ loadCards(cardsListUrl) })
                }
            }
        }

        rootView.props = CardsListView.Props(cardList(), cardListLoad())

    }

    private fun convertCardLinkToCard(cardList: List<CardLink>): ArrayList<CardsListView.Props.Card> {
        return ArrayList<CardsListView.Props.Card>().apply {
            cardList.forEach {
                add(CardsListView.Props.Card(it.name, {
                    startActivity<CardDetailsActivity>()
                    loadCardDetails(it.href)
                }))
            }
        }
    }
}
