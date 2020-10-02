package com.phanduy.tekotest.data.repository

import com.google.gson.Gson
import com.phanduy.tekotest.AppController
import com.phanduy.tekotest.api.core.TekoApi
//import com.phanduy.tekotest.data.service.factory.ApiFactory
import com.phanduy.tekotest.data.service.model.BaseResponse
import com.phanduy.tekotest.data.service.model.DetailResponse
import com.phanduy.tekotest.data.service.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

class ProductApiRepository @Inject constructor(val tekoApi: TekoApi) {

//    @Inject
//    lateinit var tekoApi: TekoApi

    suspend fun searchProduct(q: String,
                              visitorId: String,
                              channel: String,
                              terminal: String,
                              page: Int,
                              limit: Int) : SearchResponse {
        val retrievedData = tekoApi.searchProducts(q, visitorId, channel,terminal, page, limit)
        val parseDataRes = try {
            parseData(retrievedData)
        } catch (ex : Exception) {
            throw ex
        }
        return parseDataRes as SearchResponse
    }
    suspend fun getProductDetail(sku: String,
                              channel: String,
                              terminal: String) : DetailResponse? {
        val retrievedData = tekoApi.getProductDetail(sku, channel,terminal)
        val parseDataRes = try {
            parseData(retrievedData)
        } catch (ex : Exception) {
            throw ex
        }
        return parseDataRes as DetailResponse
    }
    private suspend fun parseData(retrievedData: Any) : BaseResponse? {
        var response = when(retrievedData) {
            is Deferred<*> -> {
                try {
                    when(val data = retrievedData.await()) {
                        is Response<*> -> {
                            when(data.body()) {
                                is BaseResponse -> data as Response<BaseResponse>
                                else -> null
                            }
                        }
                        else -> null
                    }
                } catch (ex : Exception) {
                    throw ex
                }
            }
            else  -> null
        }

        return when(response) {
            null -> null
            else -> if(response.isSuccessful) response.body()  else parseJson(response
                    .errorBody()?.string())

        }

    }

    suspend fun parseJson(json: String?): BaseResponse {
        var gson = Gson()
        return gson.fromJson(json, BaseResponse::class.java)
    }


//    companion object {
//        // For Singleton instantiation
//        @Volatile
//        private var instance: ProductApiRepository? = null
//
//        fun getInstance(tekoApi: TekoApi) =
//            instance
//                    ?: synchronized(this) {
//                instance
//                        ?: ProductApiRepository(tekoApi).also { instance = it }
//            }
//    }
}