package com.sigmasoftware.gwentreduxandroid.screens.cardDetails

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.sigmasoftware.gwentreduxandroid.R
import kotlinx.android.synthetic.main.card_details_view.view.*

class CardDetailsView : ConstraintLayout {

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    data class Props(val cardName: String = String(),
                     val cardFaction: String = String(),
                     val cardFlavor: String = String(),
                     val cardLoading: Boolean = false)

    var props = Props()
        set(value) {
            if (value.cardLoading) {
                cardNameTextView.visibility = GONE
                cardFlavorTextView.visibility = GONE
                cardFactionTextView.visibility = GONE
                cardDetailsProgressBar.visibility = VISIBLE
            } else {
                cardNameTextView.visibility = VISIBLE
                cardFlavorTextView.visibility = VISIBLE
                cardFactionTextView.visibility = VISIBLE
                cardDetailsProgressBar.visibility = GONE
            }
            field = value
            fillViews()
        }

    init {
        View.inflate(context, R.layout.card_details_view, this)
        fillViews()
    }

    private fun fillViews() {
        cardNameTextView.text = props.cardName
        cardFactionTextView.text = props.cardFaction
        cardFlavorTextView.text = props.cardFlavor
    }
}