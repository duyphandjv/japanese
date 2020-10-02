package com.phanduy.tekotest.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductCartDAO {

    @Query("SELECT EXISTS(SELECT 1 FROM product_cart WHERE sku = :sku LIMIT 1)")
    fun isViewed(sku : String): LiveData<Boolean>

    @Query("SELECT * FROM product_cart where sku = :sku")
    fun getProductCartBySku(sku : String): LiveData<ProductCart>

    @Query("SELECT * FROM product_cart")
    fun getAllProductInCart(): LiveData<ProductCart>

    @Query("SELECT count(sku) FROM product_cart")
    fun countProductInCart(): LiveData<Int>

    @Query("Update product_cart set number =:number where sku= :sku")
    fun changeProductNumber(sku: String, number: Int)

    @Query("Update product_cart set status =:status where sku= :sku")
    fun changeProductStatus(sku: String, status: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productFulls: List<ProductCart>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productFull: ProductCart)
}