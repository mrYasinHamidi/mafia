package com.yasin.mafia.ui.show_role

import android.annotation.SuppressLint
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.yasin.mafia.DataBase.Character
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialSharedAxis
import com.yasin.mafia.R

class ShowRoleAdapter(
    val itemListiner: ShowItemListiner
) :
    RecyclerView.Adapter<ShowRoleViewHolder>() {
    var cards: List<Card> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowRoleViewHolder {
        return ShowRoleViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ShowRoleViewHolder, position: Int) {
        holder.bind(cards[position], itemListiner)
    }

}

class ShowRoleViewHolder private constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val cardView = itemView.findViewById<MaterialCardView>(R.id.show_card)
    val textView = itemView.findViewById<TextView>(R.id.show_item_text)
    val img = itemView.findViewById<ImageView>(R.id.show_item_img)

    @SuppressLint("ClickableViewAccessibility")
    fun bind(
        item: Card,
        itemListiner: ShowItemListiner
    ) {
        textView.text = item.name

        cardView.setOnClickListener {
            itemListiner.onItemClick(item, cardView, img)
        }
        cardView.setOnLongClickListener {
            itemListiner.onItemClick(item, cardView, img)
            true
        }
    }


    companion object {
        fun from(parent: ViewGroup): ShowRoleViewHolder {
            TransitionManager.beginDelayedTransition(
                parent,
                MaterialSharedAxis.create(MaterialSharedAxis.Z, false)
            )
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.show_list_item, parent, false)
            return ShowRoleViewHolder(view)
        }
    }
}


class ShowItemListiner(val listiner: (card: Card, view: View, img: ImageView) -> Unit) {
    fun onItemClick(card: Card, view: View, img: ImageView) =
        listiner(card, view, img)
}

data class Card(val name: String, val character: Character, var isShown: Boolean = false)