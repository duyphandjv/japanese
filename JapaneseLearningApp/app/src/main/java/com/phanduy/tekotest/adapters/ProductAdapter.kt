package com.phanduy.tekotest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.phanduy.tekotest.data.service.model.ProductBase
import com.phanduy.tekotest.databinding.ListItemProductBinding
import kotlin.collections.ArrayList

class ProductAdapter : RecyclerView.Adapter< RecyclerView.ViewHolder>() {

    private var listProducts = ArrayList<ProductBase>()

    fun addDatas(data : ArrayList<ProductBase>?, isRefresh : Boolean) {

        if(data == null) return

        if(isRefresh) {
            listProducts.clear()
            listProducts = data
            notifyDataSetChanged()
            return
        }

//        listProducts.addAll(data)
//        notifyDataSetChanged()

        val initPosition = listProducts.size
        listProducts.addAll(data)
        notifyItemRangeInserted(initPosition, listProducts.size)
    }

    override fun getItemCount(): Int = listProducts.size

    private fun getItem(position: Int) = if(position < listProducts.size) listProducts.get(position) else null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ProductViewHolder(ListItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position) ?: return

        (holder as ProductViewHolder).bind(product)
    }
}

private class ProductDiffCallback : DiffUtil.ItemCallback<ProductBase>() {

    override fun areItemsTheSame(oldItem: ProductBase, newItem: ProductBase): Boolean {
        return oldItem.sku.equals(newItem.sku)
    }

    override fun areContentsTheSame(oldItem: ProductBase, newItem: ProductBase): Boolean {
        return oldItem == newItem
    }
}