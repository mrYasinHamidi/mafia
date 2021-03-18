package com.yasin.mafia.ui.characters
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialSharedAxis
import com.yasin.mafia.DataBase.Character
import com.yasin.mafia.databinding.CharactersListItemBinding

class charactersAdapter(
    val viewModel: CharacterViewModel,
    val itemListiner: StartItemListiner
) :
    ListAdapter<Character, charactersAdapter.CharacterViewHolder>(CharacterDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), itemListiner, viewModel)
    }

    class CharacterViewHolder private constructor(
        val binding: CharactersListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Character,
            itemListiner: StartItemListiner,
            viewModel: CharacterViewModel
        ) {
            binding.characterName.text = item.role

            // load image
            var photoName: String = item.image
            if (photoName.contains(".")) {
                photoName = photoName.substring(0, photoName.lastIndexOf('.'))
            }
            val imageResId: Int = itemView.getResources().getIdentifier(
                photoName, "drawable", itemView.context.getApplicationContext().getPackageName()
            )
            binding.characterImage.setImageResource(imageResId)

//            cardView : back from back stack
            if (item.selected) {
                binding.characterCard.isChecked = true
            } else {
                binding.characterCard.isChecked = false
            }

            binding.characterCard.setOnClickListener {
                if (viewModel.Limit > 0) {
                    if ((it as MaterialCardView).isChecked)
                        viewModel.Limit++
                    else
                        viewModel.Limit--

                    click(itemListiner, item)

                } else {
                    if ((it as MaterialCardView).isChecked) {
                        viewModel.Limit++
                        click(itemListiner, item)
                    } else
                        Toast.makeText(
                            it.context,
                            "بیشتر از این بازیکن نداری :)",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                }
            }

        }

        private fun click(
            itemListiner: StartItemListiner,
            item: Character
        ) {
            binding.characterCard.isChecked = !binding.characterCard.isChecked
            itemListiner.onItemClick(item, binding.characterCard.isChecked)
        }


        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                TransitionManager.beginDelayedTransition(
                    parent,
                    MaterialSharedAxis.create(MaterialSharedAxis.Z, false)
                )
                val inflater = LayoutInflater.from(parent.context)
                val binding = CharactersListItemBinding.inflate(inflater, parent, false);
                return CharacterViewHolder(binding)
            }
        }
    }
}

class CharacterDiffUtil : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}

class StartItemListiner(val listiner: (person: Character, isChecked: Boolean) -> Unit) {
    fun onItemClick(person: Character, isChecked: Boolean) = listiner(person, isChecked)
}
