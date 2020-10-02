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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.phanduy.tekotest.AppInfo
import com.phanduy.tekotest.R
import com.phanduy.tekotest.data.service.model.Image
import com.phanduy.tekotest.databinding.ImageItemBinding
import java.util.*


class ImagePagerAdapter constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var images: ArrayList<Image>? = null

    override fun getItemCount() : Int {
        if(images == null) return 0

        return images!!.size
    }

    fun getImage(position: Int) : Image? {
        if(images == null) return null
        return images!!.get(position)
    }

    fun updateData(data: ArrayList<Image> ?) {
        images = data
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        return ImageFragment(getImage(position))
    }
}

class ImageFragment constructor(private val image : Image?) : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<ImageItemBinding>(
                inflater, R.layout.image_item, container, false
        ).apply {
            imageData = image

            imageContainer.layoutParams = ViewGroup.LayoutParams(AppInfo.width!!, AppInfo.width!!)

            executePendingBindings()

        }

        return binding.root
    }
}