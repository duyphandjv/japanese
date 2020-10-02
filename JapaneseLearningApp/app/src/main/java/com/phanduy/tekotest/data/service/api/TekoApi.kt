package com.phanduy.tekotest.api.core

import com.phanduy.tekotest.data.service.model.DetailResponse
import com.phanduy.tekotest.data.service.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TekoApi{

    @GET("search/")
    fun searchProducts(@Query("q") q: String,
                       @Query("visitorId") visitorId: String,
                       @Query("channel") channel: String,
                       @Query("terminal") terminal: String,
                       @Query("_page") page: Int,
                       @Query("_limit") limit: Int
                            ): Deferred<Response<SearchResponse>>

    @GET("products/{productSku}")
    fun getProductDetail(
                        @Path("productSku") sku: String,
                        @Query("channel") channel: String,
                         @Query("terminal") terminal: String): Deferred<Response<DetailResponse>>


}