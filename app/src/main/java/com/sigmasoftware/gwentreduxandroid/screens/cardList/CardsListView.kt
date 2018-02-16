package com.sigmasoftware.gwentreduxandroid.screens.cardList

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.sigmasoftware.gwentreduxandroid.R
import kotlinx.android.synthetic.main.card_list_view.view.*

class CardsListView : ConstraintLayout {

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    data class Props(val cardList: List<Card> = ArrayList(), val listLoad: ListLoad = ListLoad.Unavailable()) {
        data class Card(val name: String, val clickListener: () -> Unit)
        sealed class ListLoad {
            data class Available(val loadMore: () -> Unit = { }) : ListLoad()
            class Unavailable : ListLoad()
            class Loading : ListLoad()
            data class Failed(val errorMessage: String = String()) : ListLoad()
        }
    }

    private var infiniteScrollListener = InfiniteScrollListener({}, LinearLayoutManager(context))

    var props = Props()
        set(value) {
            cardListAdapter.props = value.cardList
            cardListAdapter.notifyDataSetChanged()

            when {
                value.listLoad is Props.ListLoad.Available && value.cardList.isNotEmpty() -> {
                    cardRecyclerView.removeOnScrollListener(infiniteScrollListener)
                    infiniteScrollListener = InfiniteScrollListener({ value.listLoad.loadMore() }, cardRecyclerView.layoutManager as LinearLayoutManager).apply {
                        cardRecyclerView.addOnScrollListener(this)
                    }
                    showOnlyRecyclerView()
                }
                value.listLoad is Props.ListLoad.Failed -> {
                    showOnlyRecyclerView()
                    Toast.makeText(context, value.listLoad.errorMessage, Toast.LENGTH_SHORT).show()
                }
                value.listLoad is Props.ListLoad.Unavailable -> {
                    cardRecyclerView.removeOnScrollListener(infiniteScrollListener)
                }
                value.listLoad is Props.ListLoad.Loading -> {
                    showLoadingMoreProgressBar()
                }
                value.cardList.isNotEmpty() -> {
                    showOnlyRecyclerView()
                }
                value.listLoad is Props.ListLoad.Available -> {
                    showOnlyStartButton()
                    startButton.setOnClickListener {
                        value.listLoad.loadMore()
                    }
                }
            }
            field = value
        }

    private val cardListAdapter = CardListAdapter(context, props.cardList)

    init {
        View.inflate(context, R.layout.card_list_view, this)
        cardRecyclerView.adapter = cardListAdapter
        cardRecyclerView.layoutManager = LinearLayoutManager(context)
        cardRecyclerView.addOnScrollListener(infiniteScrollListener)
    }

    private fun showOnlyStartButton() {
        startButton.visibility = VISIBLE
        progressBar.visibility = GONE
        cardRecyclerView.visibility = GONE
    }

    private fun showOnlyRecyclerView() {
        startButton.visibility = GONE
        progressBar.visibility = GONE
        cardRecyclerView.visibility = VISIBLE
    }

    private fun showLoadingMoreProgressBar() {
        startButton.visibility = GONE
        progressBar.visibility = VISIBLE
        cardRecyclerView.visibility = VISIBLE
    }
}