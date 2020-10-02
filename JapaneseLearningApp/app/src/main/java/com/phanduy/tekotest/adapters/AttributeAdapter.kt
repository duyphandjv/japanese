package com.phanduy.tekotest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.phanduy.tekotest.R
import com.phanduy.tekotest.data.service.model.Attribute
import com.phanduy.tekotest.databinding.ListItemAttributeBinding


class AttributeAdapter : androidx.recyclerview.widget.ListAdapter<Attribute, RecyclerView.ViewHolder>(AttributeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return AttributeViewHolder(ListItemAttributeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as AttributeViewHolder).bind(product, position)
    }

    class AttributeViewHolder(
        private val binding: ListItemAttributeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Attribute, position: Int) {
            binding.apply {
                attribute = item
                if(position == 0) {
                    rootView.background = rootView.context.resources.getDrawable(R.drawable.bg_attribute_first_item)
                }
                else if(position % 2 == 0) {
                    rootView.setBackgroundColor(rootView.context.resources.getColor(R.color.silver))
                } else {
                    rootView.setBackgroundColor(rootView.context.resources.getColor(R.color.white))
                }

                executePendingBindings()
            }
        }
    }
}

private class AttributeDiffCallback : DiffUtil.ItemCallback<Attribute>() {

    override fun areItemsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
        return oldItem.name.equals(newItem.name)
    }

    override fun areContentsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
        return oldItem == newItem
    }
}