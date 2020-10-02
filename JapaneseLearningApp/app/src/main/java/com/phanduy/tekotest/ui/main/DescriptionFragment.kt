package com.phanduy.tekotest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.phanduy.tekotest.adapters.AttributeAdapter
import com.phanduy.tekotest.databinding.FragmentDescriptionBinding
import com.phanduy.tekotest.viewmodel.ProductDetailViewModel

class DescriptionFragment constructor(private val productDetailViewModel: ProductDetailViewModel): Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = AttributeAdapter()
        binding.attributeList.adapter = adapter

        subcribeUi(adapter, binding)

        return binding.root
    }

    fun subcribeUi(adapter: AttributeAdapter, binding : FragmentDescriptionBinding) {
        productDetailViewModel.data.observe(viewLifecycleOwner, Observer {
            binding.hasAtributes = !productDetailViewModel.data.value?.getResultData()?.attributeGroups!!.isEmpty()
            adapter.submitList(productDetailViewModel.data.value?.getResultData()?.attributeGroups)
        })
    }
}