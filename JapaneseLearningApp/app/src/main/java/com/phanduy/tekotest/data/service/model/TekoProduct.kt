package com.phanduy.tekotest.data.service.model

import androidx.room.Entity
import com.phanduy.tekotest.utilities.StringUtils
import java.util.ArrayList

data class Seller(val id: Int, val name: String, val displayName: String)

data class Brand(val code: String, val name: String)

data class Image(val url: String, val path: String, val priority: Int)

data class Status(val publish: Boolean, val sale: String)

data class Objective(
        val code: String,
        val name: String)

data class ProductType(val code: String, val name: String)

data class Price(val supplierSalePrice: Int, val sellPrice: Int) {
    fun getSalePriceDisplay(): String? {
        if (supplierSalePrice > 0) {
            return StringUtils.getPriceString(supplierSalePrice)
        } else {
            return "Ngừng kinh doanh"
        }
    }

    fun getSellPriceDisplay(): String? {
        if (sellPrice > 0) {
            return StringUtils.getPriceString(sellPrice)
        } else {
            return ""
        }
    }

    fun isHasPrice() :Boolean {
        return supplierSalePrice > 0
    }
}

@Entity
data class ProductLine(
       val code: String,
       val name: String)

@Entity
data class PromotionPrice(val channel: String, val terminal: String, val finalPrice: Int, val promotionPrice: Int, val bestPrice: Int, val flashSalePrice: Int)

@Entity
data class Attribute(val name: String, val value: String)

data class ProductBase(val sku: String,
                       val name: String,
                       var price: Price,
                       var promotionPrices: ArrayList<PromotionPrice>,
                       var images: ArrayList<Image>) {

    fun getMainImage(): String? {
        if (images.isEmpty()) return null
        return images.get(0).url
    }


}

data class ProductFull(val sku: String,
                       val name: String,
                       var price: Price,
                       var promotionPrices: ArrayList<PromotionPrice>,
                       var images: ArrayList<Image>,
                       val objective: Objective,
                       val productLine: ProductLine,
                       val attributeGroups: ArrayList<Attribute>
) {
    fun isHasImageAvail(): Boolean {
        return images.isEmpty()
    }

    fun isNoNeedTab(): Boolean {
        return images.isEmpty() || images.size <= 1
    }


}