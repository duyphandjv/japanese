/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phanduy.tekotest.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.phanduy.tekotest.data.db.ProductCart
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.data.repository.ProductCartRepository
import com.phanduy.tekotest.data.service.*
import com.phanduy.tekotest.data.service.model.DetailResponse
import com.phanduy.tekotest.data.service.model.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ProductDetailViewModel(
        private val apiRepository: ProductApiRepository,
        private val productCartRepository: ProductCartRepository,
        private val sku: String,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    @Inject
//    lateinit var apiRepository: ProductApiRepository
//
//    @Inject
//    lateinit var productCartRepository: ProductCartRepository

    var price: Int? = null
    var productLine: String? = null

    val data  : LiveData<DetailResponse?> = liveData(Dispatchers.IO) {

        val response = try {
            apiRepository.getProductDetail(sku, "pv_online","CP01")
        } catch (ex: Exception) {
            DetailResponse(null).also {
                it.ex = ex
            }
        }

        if(response != null && response.getResultData() != null) {
            price = response.getResultData()!!.price.supplierSalePrice
            productLine = response.getResultData()!!.productLine.name
        }

        emit(response)
    }

    val relateProduct : LiveData<SearchResponse?> = data.switchMap {
        setProductCount(1)
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val response = if(productLine == null) null else try {
                apiRepository.searchProduct(productLine!!, "","pv_online","CP01", 1, 10)
            } catch (ex: Exception) {
                null
            }
            emit(response)

        }
    }

    val cartCount = productCartRepository.countProductInCart()

    val productNumber = getProductCount()

    val totalPrice : LiveData<Int> = getProductCount().switchMap {

        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            if(it != null && price != null) {
                emit(it * price!!)
            }
            else {
                emit(0)
            }
        }
    }

    fun increaseNumber() {
        viewModelScope.launch {
            setProductCount(productNumber.value!!.inc())
        }
    }

    fun decreaseNumber() {
        viewModelScope.launch {
            if(productNumber.value!! > 1) {
                setProductCount(productNumber.value!!.dec())
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            productCartRepository.insert(ProductCart(sku, productNumber.value!!, 0))
        }
    }

    companion object {
        private const val PRODUCT_COUNT_KEY = "PRODUCT_COUNT_KEY"
    }

    private fun setProductCount(count : Int) {
        savedStateHandle.set(PRODUCT_COUNT_KEY, count)
    }

    private fun getProductCount() : MutableLiveData<Int>{
        return savedStateHandle.getLiveData<Int>(PRODUCT_COUNT_KEY, 1)
    }
}