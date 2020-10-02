package com.phanduy.tekotest.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.phanduy.tekotest.data.JapaneseWord
import com.phanduy.tekotest.databinding.ItemJapaneseGameBinding

class JapaneseGameHolder(
    private val binding: ItemJapaneseGameBinding
) : RecyclerView.ViewHolder(binding.root)  {
    fun bind(item: JapaneseWord) {
        binding.apply {
            word = item
            executePendingBindings()
        }
    }
}