package com.yasin.mafia.ui.start

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.yasin.mafia.DataBase.Person
import com.yasin.mafia.R

class StartAdapter(
    val onDleteClickListiner: StarDeletetListiner,
    val onItemClickListener: StartItemListiner
) :
    ListAdapter<Person, StartAdapter.InsertViewHolder>(InsertDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsertViewHolder {
        return InsertViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: InsertViewHolder, position: Int) {
        val item: Person = getItem(position)
        holder.bind(item, onDleteClickListiner, onItemClickListener)
    }

    class InsertViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.btn_delete_list)
        val cardView = itemView.findViewById<MaterialCardView>(R.id.cardView)
        val textView = itemView.findViewById<TextView>(R.id.start_list_text)
        fun bind(
            item: Person,
            clickLitener: StarDeletetListiner,
            onItemClickListener: StartItemListiner
        ) {
            textView.text = item.name

            //CardView
            cardView.isChecked = item.isChecked
            cardView.setOnClickListener { view ->
                cardView.setChecked(!cardView.isChecked())
                cardView.isHovered = !cardView.isHovered
                onItemClickListener.onItemClick(item, cardView.isChecked)
            }

            imageView.setOnClickListener { clickLitener.onDeleteClick(item,cardView.isChecked) }
        }

        companion object {
            fun from(parent: ViewGroup): InsertViewHolder {

                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.person_list_item, parent, false)
                return InsertViewHolder(view)
            }
        }
    }
}


class InsertDiffUtil() : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.personId == newItem.personId
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}

class StarDeletetListiner(val listiner: (person: Person, isChecked: Boolean) -> Unit) {
    fun onDeleteClick(person: Person, isChecked: Boolean) = listiner(person,isChecked)
}

class StartItemListiner(val listiner: (person: Person, isChecked: Boolean) -> Unit) {
    fun onItemClick(person: Person, isChecked: Boolean) = listiner(person, isChecked)
}
