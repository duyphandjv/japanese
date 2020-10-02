/*
 * Copyright 2019 Google LLC
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

package com.phanduy.tekotest.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.phanduy.tekotest.ui.main.AttributeFragment
import com.phanduy.tekotest.ui.main.DescriptionFragment
import com.phanduy.tekotest.viewmodel.ProductDetailViewModel

const val PRODUCT_DESCRIPTION_PAGE_INDEX = 0
const val SPECTATION_PAGE_INDEX = 1
const val PRICE_COMPARE_PAGE_INDEX = 2

class DescriptionPagerAdapter constructor(fragment: Fragment, viewModel: ProductDetailViewModel) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
            PRODUCT_DESCRIPTION_PAGE_INDEX to { DescriptionFragment(viewModel) },
            SPECTATION_PAGE_INDEX to { AttributeFragment(viewModel) },
                    PRICE_COMPARE_PAGE_INDEX to { DescriptionFragment(viewModel) }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
