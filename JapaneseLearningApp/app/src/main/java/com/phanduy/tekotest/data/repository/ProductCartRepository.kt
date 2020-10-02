package com.phanduy.tekotest.data.repository

import com.phanduy.tekotest.data.db.ProductCart
import com.phanduy.tekotest.data.db.ProductCartDAO
import javax.inject.Singleton

@Singleton
class ProductCartRepository (private val productCartDAO: ProductCartDAO) {

    fun isViewed(sku : String) = productCartDAO.isViewed(sku)

    fun getProductCartBySku(sku: String) = productCartDAO.getProductCartBySku(sku)

    fun getAllProductCart() = productCartDAO.getAllProductInCart()

    fun countProductInCart() = productCartDAO.countProductInCart()

    suspend fun changeProductNumber(sku: String, number : Int) {
        productCartDAO.changeProductNumber(sku, number)
    }

    suspend fun addToCart(productCart: ProductCart) {
        productCartDAO.changeProductStatus(productCart.sku, 1)
    }

    suspend fun insert(productCart: ProductCart) {
        productCartDAO.insert(productCart)
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ProductCartRepository? = null

        fun getInstance(productDAO: ProductCartDAO) =
            instance ?: synchronized(this) {
                instance ?: ProductCartRepository(productDAO).also { instance = it }
            }
    }
}