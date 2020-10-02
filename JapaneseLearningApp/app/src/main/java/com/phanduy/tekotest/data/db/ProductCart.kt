package com.phanduy.tekotest.data.db

import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phanduy.tekotest.utilities.StringUtils
import kotlinx.coroutines.launch

@Entity(tableName = "product_cart")
data class ProductCart (
        @PrimaryKey @ColumnInfo(name = "sku")val sku: String,
        var number: Int,
        val status: Int// 0: reviewing, 1: added to cart, -1: deleted
)