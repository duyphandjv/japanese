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

package com.phanduy.tekotest.factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.phanduy.tekotest.data.db.AppDatabase
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.data.repository.ProductCartRepository
import com.phanduy.tekotest.viewmodel.ProductDetailViewModel

class ProductDetailViewModelFactory(
        val productApiRepository: ProductApiRepository,
        val productCartRepository: ProductCartRepository,
        private val sku: String,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
    ): T {
        return ProductDetailViewModel(productApiRepository, productCartRepository, sku, handle) as T
    }
}
