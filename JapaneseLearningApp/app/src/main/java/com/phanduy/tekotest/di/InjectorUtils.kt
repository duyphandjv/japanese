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

package com.phanduy.tekotest.di

import androidx.fragment.app.Fragment
import com.phanduy.tekotest.AppController
import com.phanduy.tekotest.api.core.TekoApi
import com.phanduy.tekotest.data.db.AppDatabase
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.data.repository.ProductCartRepository
import com.phanduy.tekotest.factory.ProductDetailViewModelFactory
import com.phanduy.tekotest.factory.ProductListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

//    fun provideProductListViewModelFactory(
//            tekoApi: TekoApi,
//            fragment: Fragment
//    ): ProductListViewModelFactory {
//        return ProductListViewModelFactory(ProductApiRepository(tekoApi), fragment)
//    }

    fun provideProductListViewModelFactory(
            productApiRepository: ProductApiRepository,
            fragment: Fragment
    ): ProductListViewModelFactory {
        return ProductListViewModelFactory(productApiRepository, fragment)
    }

    fun provideProductDetailViewModelFactory(
            productApiRepository: ProductApiRepository,
            productCartRepository: ProductCartRepository,
            fragment: Fragment,
        sku: String
    ): ProductDetailViewModelFactory {
        return ProductDetailViewModelFactory(productApiRepository,
                productCartRepository,
                sku, fragment)
    }
}
