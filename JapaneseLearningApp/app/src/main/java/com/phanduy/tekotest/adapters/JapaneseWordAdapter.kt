package com.phanduy.tekotest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phanduy.tekotest.adapters.holders.JapaneseGameHolder
import com.phanduy.tekotest.data.JapaneseWord
import com.phanduy.tekotest.databinding.ItemJapaneseGameBinding

class JapaneseWordAdapter : RecyclerView.Adapter< RecyclerView.ViewHolder>() {

    private var listWords = ArrayList<JapaneseWord>()

    fun addDatas(words : ArrayList<JapaneseWord>?, isRefresh : Boolean) {

        if(words == null) return

        if(isRefresh) {
            listWords.clear()
            listWords = words
            notifyDataSetChanged()
            return
        }
        val initPosition = listWords.size
        listWords.addAll(words)
        notifyItemRangeInserted(initPosition, listWords.size)
    }

    override fun getItemCount(): Int = listWords.size

    private fun getItem(position: Int) = if(position < listWords.size) listWords.get(position) else null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return JapaneseGameHolder(ItemJapaneseGameBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val word = getItem(position) ?: return
        (holder as JapaneseGameHolder).bind(word)
    }
}