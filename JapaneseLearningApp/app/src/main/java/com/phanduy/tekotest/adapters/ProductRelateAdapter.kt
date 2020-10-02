package com.phanduy.tekotest.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.phanduy.tekotest.data.service.model.ProductBase
import com.phanduy.tekotest.databinding.ListItemRelateProductBinding
import com.phanduy.tekotest.ui.main.ListProductPreviewFragmentDirections


class ProductRelateAdapter : androidx.recyclerview.widget.ListAdapter<ProductBase, RecyclerView.ViewHolder>(ProductRelateDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ProductViewHolder(ListItemRelateProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as ProductViewHolder).bind(product)
    }

    class ProductViewHolder(
        private val binding: ListItemRelateProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
//                binding.product?.let { product ->
//                    navigateToDetail(product.sku, it)
//                }
            }

//            binding.rootView.layoutParams = ViewGroup.LayoutParams(AppInfo.width!! * 4 / 9 , ViewGroup.LayoutParams.WRAP_CONTENT )
//            binding.image.layoutParams = ConstraintLayout.LayoutParams(AppInfo.width!! * 4 / 9 - 20, AppInfo.width!! * 4 / 9 - 20)

            binding.promotionPrice.setPaintFlags(binding.promotionPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        }

        private fun navigateToDetail(
                sku: String,
                view: View
        ) {
            val direction =
                    ListProductPreviewFragmentDirections.actionListProductFragmentToProductDetailFragment(
                            sku
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: ProductBase) {
            binding.apply {
                product = item
                executePendingBindings()
            }
        }
    }
}

private class ProductRelateDiffCallback : DiffUtil.ItemCallback<ProductBase>() {

    override fun areItemsTheSame(oldItem: ProductBase, newItem: ProductBase): Boolean {
        return oldItem.sku.equals(newItem.sku)
    }

    override fun areContentsTheSame(oldItem: ProductBase, newItem: ProductBase): Boolean {
        return oldItem == newItem
    }
}