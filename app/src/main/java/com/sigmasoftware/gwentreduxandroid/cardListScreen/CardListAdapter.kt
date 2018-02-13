package com.sigmasoftware.gwentreduxandroid.cardListScreen

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sigmasoftware.gwentreduxandroid.R
import com.sigmasoftware.gwentreduxandroid.cardListScreen.views.CardsListView
import kotlinx.android.synthetic.main.card_cell.view.*

class CardListAdapter(val context: Context, var props: List<CardsListView.Props.Card>) : RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        return CardViewHolder(View.inflate(context, R.layout.card_cell, null))
    }

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        holder?.bind(props[position])
    }

    override fun getItemCount(): Int {
        return props.size
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(card: CardsListView.Props.Card) {
            itemView.cardName.text = card.name
        }
    }
}