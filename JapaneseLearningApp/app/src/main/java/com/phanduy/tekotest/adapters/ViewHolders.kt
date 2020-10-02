package com.phanduy.tekotest.adapters

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.phanduy.tekotest.data.service.model.ProductBase
import com.phanduy.tekotest.databinding.ListItemProductBinding

class ProductViewHolder(
        private val binding: ListItemProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.setClickListener {
            binding.product?.let { product ->
                navigateToDetail(product.sku, it)
            }
        }
        binding.promotionPrice.setPaintFlags(binding.promotionPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
    }

    private fun navigateToDetail(
            sku: String,
            view: View
    ) {

    }

    fun bind(item: ProductBase) {

        binding.apply {
            product = item
            executePendingBindings()
        }
    }
}