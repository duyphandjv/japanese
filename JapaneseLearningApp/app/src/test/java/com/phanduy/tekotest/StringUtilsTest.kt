package com.phanduy.tekotest

import com.phanduy.tekotest.utilities.StringUtils
import org.junit.Assert
import org.junit.Test

class StringUtilsTest {
    @Test
    fun formatPrice() {
        Assert.assertEquals("4,000 đ", StringUtils.getPriceString(4000))
        Assert.assertEquals("40,000 đ", StringUtils.getPriceString(40000))
        Assert.assertEquals("400,000 đ", StringUtils.getPriceString(400000))
        Assert.assertEquals("4,000,000 đ", StringUtils.getPriceString(4000000))
        Assert.assertEquals("40,000,000 đ", StringUtils.getPriceString(40000000))
    }
}