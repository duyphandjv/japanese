package com.phanduy.tekotest.utilities

import java.text.DecimalFormat

object StringUtils {
    fun getPriceString(price : Int) : String {
        val dec = DecimalFormat("#,###")
        return "${dec.format(price)} đ"
    }
}