package com.sigmasoftware.gwentreduxandroid.screen.cardList

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

    data class Props(val cardList: List<Card> = ArrayList(),
                     val start: (() -> Unit)? = null,
                     val errorMessage: String = String()) {
        data class Card(val name: String, val clickListener: () -> Unit)

    }

    var props = Props()
        set(value) {
            cardListAdapter.props = value.cardList
            cardListAdapter.notifyDataSetChanged()
            when {
                value.cardList.isNotEmpty() -> {
                    startButton.visibility = GONE
                    progressBar.visibility = GONE
                    cardRecyclerView.visibility = VISIBLE
                }
                value.errorMessage.isNotEmpty() -> {
                    startButton.visibility = VISIBLE
                    progressBar.visibility = GONE
                    cardRecyclerView.visibility = GONE
                    Toast.makeText(context, value.errorMessage, Toast.LENGTH_SHORT).show()
                }
                value.start != null -> {
                    startButton.visibility = VISIBLE
                    progressBar.visibility = GONE
                    cardRecyclerView.visibility = GONE
                }
                else -> {
                    startButton.visibility = GONE
                    progressBar.visibility = VISIBLE
                    cardRecyclerView.visibility = GONE
                }
            }
            field = value
        }

    private val cardListAdapter = CardListAdapter(context, props.cardList)

    init {
        View.inflate(context, R.layout.card_list_view, this)
        cardRecyclerView.adapter = cardListAdapter
        cardRecyclerView.layoutManager = LinearLayoutManager(context)
        startButton.setOnClickListener {
            props.start?.invoke()
        }
    }
}