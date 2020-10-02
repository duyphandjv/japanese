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

package com.phanduy.tekotest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.phanduy.tekotest.data.db.AppDatabase
import com.phanduy.tekotest.data.db.ProductCart
import com.phanduy.tekotest.data.db.ProductCartDAO
import com.phanduy.tekotest.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductCartDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var productCartDAO: ProductCartDAO
    private val productCartA = ProductCart("1", 1, 1)
    private val productCartB = ProductCart("2", 2, 0)
    private val productCartC = ProductCart("3", 1, 0)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productCartDAO = database.productCartDao()

        productCartDAO.insertAll(listOf(productCartA, productCartB, productCartC))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetProductCartCount() {
        val count = getValue(productCartDAO.countProductInCart())
        assertThat(count, equalTo(3))
    }

}