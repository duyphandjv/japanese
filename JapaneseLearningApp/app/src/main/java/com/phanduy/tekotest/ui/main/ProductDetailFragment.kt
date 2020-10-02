package com.phanduy.tekotest.ui.main

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.phanduy.tekotest.R
import com.phanduy.tekotest.adapters.*
import com.phanduy.tekotest.appComponent
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.data.repository.ProductCartRepository
import com.phanduy.tekotest.databinding.FragmentProductDetailBinding
import com.phanduy.tekotest.di.InjectingSavedStateViewModelFactory
import com.phanduy.tekotest.di.InjectorUtils
import com.phanduy.tekotest.viewmodel.ListProductViewModel
import com.phanduy.tekotest.viewmodel.ProductDetailViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProductDetailFragment : Fragment() {

    @Inject
    lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

    @Inject
    lateinit var productApiRepository: ProductApiRepository

    @Inject
    lateinit var productCartRepository: ProductCartRepository

    lateinit var sku: String
//    private val args: ProductDetailFragmentArgs by navArgs()

    lateinit var productDetailViewModel : ProductDetailViewModel

//    private val productDetailViewModel: ProductDetailViewModel by viewModels {
//        InjectorUtils.provideProductDetailViewModelFactory(this, args.sku)
//    }

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//
        sku = arguments?.getString("sku")!!

        val factory = abstractViewModelFactory.createProductDetailFactory(sku, productApiRepository, productCartRepository, this)

//        productDetailViewModel = ViewModelProviders.of(this, InjectorUtils.provideProductDetailViewModelFactory(productApiRepository, productCartRepository, this, sku)).get(ProductDetailViewModel::class.java)
        productDetailViewModel = ViewModelProviders.of(this, factory).get(ProductDetailViewModel::class.java)
        val adapter = ImagePagerAdapter(requireParentFragment())
        val relateProductAdapter = ProductRelateAdapter()

        val binding = DataBindingUtil.inflate<FragmentProductDetailBinding>(
            inflater, R.layout.fragment_product_detail, container, false
        ).apply {

            viewModel = productDetailViewModel
            lifecycleOwner = viewLifecycleOwner

            callback = object : Callback {
                override fun onBack() {
                    view!!.findNavController().navigateUp()
                }

                override fun goToCart() {
                    doGoToCart()
                    Snackbar.make(root, R.string.function_not_implement, Snackbar.LENGTH_LONG)
                            .show()
                }

                override fun increase() {

                    let {
                        viewModel!!.increaseNumber()
                    }


                }

                override fun decrease() {
                    let {
                        viewModel!!.decreaseNumber()
                    }
                }

                override fun addToCart() {


                    let {
                        viewModel!!.addToCart()
                        Snackbar.make(root, R.string.added_to_cart, Snackbar.LENGTH_LONG)
                                .show()
                    }

                }

            }

            promotionPrice.setPaintFlags(promotionPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

            viewPager.adapter = adapter

            TabLayoutMediator(intoTabLayout, viewPager)
            { _, _ ->}.attach()


            productList.adapter = relateProductAdapter


            viewPagerDescription.adapter = DescriptionPagerAdapter(requireParentFragment(), productDetailViewModel)

            // Set the icon and text for each tab
            TabLayoutMediator(tabs, viewPagerDescription) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()
        }

        subscribeUi(adapter, relateProductAdapter, binding)


        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            PRODUCT_DESCRIPTION_PAGE_INDEX -> getString(R.string.tab_description)
            SPECTATION_PAGE_INDEX -> getString(R.string.tab_spec)
            PRICE_COMPARE_PAGE_INDEX -> getString(R.string.tab_price_compare)
            else -> null
        }
    }

    private fun subscribeUi(adapter: ImagePagerAdapter, relateProductAdapter: ProductRelateAdapter, binding : FragmentProductDetailBinding) {

        productDetailViewModel.data.observe(viewLifecycleOwner, Observer {
            if(it != null && it.result != null) {
                val images = it.getResultData()!!.images

                if(images.isEmpty()) {
                    binding.hasImage = false
                } else {
                    binding.hasImage = true
                    adapter.updateData(it.getResultData()!!.images)
                }

            }

        })

        productDetailViewModel.relateProduct.observe(viewLifecycleOwner, Observer {
            if(it != null && it.isHasData()) {
                relateProductAdapter.submitList(it.getResultData())
            }

        })
    }

    fun doGoToCart() {

    }

    interface Callback {
        fun onBack()
        fun goToCart()
        fun increase()
        fun decrease()
        fun addToCart()
    }
}