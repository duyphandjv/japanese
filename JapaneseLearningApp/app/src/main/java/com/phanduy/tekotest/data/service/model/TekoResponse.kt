package com.phanduy.tekotest.data.service.model

import android.content.Context
import com.phanduy.tekotest.R
import java.lang.Exception
import java.net.UnknownHostException


sealed class BaseResponse() {
    var code: String? = null
    var message: String? = null
    var ex: Exception? = null
    fun isSuccess() : Boolean {
        return code != null && code.equals("SUCCESS")
    }

    fun setResponseInfo(code : String, message : String) {
        this.code = code
        this.message = message
    }

    fun setException(ex : Exception) {
        this.ex = ex
    }

    fun isHasMessage() : Boolean {
        return !message.isNullOrEmpty()
    }

    fun getErrorMessage(context: Context) : String {
        if(ex != null && ex is UnknownHostException) {
            return context.getString(R.string.network_error)
        } else if(!message.isNullOrEmpty()) {
            return message!!
        } else {
            return context.getString(R.string.server_error)
        }
    }

    abstract fun getResultData() : Any?
}

data class SearchResult(val products: ArrayList<ProductBase>)

data class Extra(val totalItems: Int, val page: Int, val pageSize: Int)

data class DetailResult(val product: ProductFull)

class SearchResponse(val result: SearchResult?, val extra: Extra?) : BaseResponse() {
    override fun getResultData() : ArrayList<ProductBase>? {
        return result?.products
    }

    fun isHasData(): Boolean {
        return !result?.products.isNullOrEmpty()
    }

}

class DetailResponse(val result: DetailResult?) : BaseResponse() {
    override fun getResultData() : ProductFull? {
        return result?.product
    }
}