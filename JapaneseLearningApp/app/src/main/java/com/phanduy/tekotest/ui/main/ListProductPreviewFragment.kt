package com.phanduy.tekotest.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.phanduy.tekotest.R
import com.phanduy.tekotest.adapters.ProductAdapter
import com.phanduy.tekotest.appComponent
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.databinding.FragmentListProductsBinding
import com.phanduy.tekotest.di.InjectingSavedStateViewModelFactory
import com.phanduy.tekotest.ui.main.extensions.CustomScrollListener
import com.phanduy.tekotest.di.InjectorUtils
import com.phanduy.tekotest.utilities.NetworkUtil
import com.phanduy.tekotest.viewmodel.ListProductViewModel
import dagger.android.support.AndroidSupportInjection
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject


class ListProductPreviewFragment : BaseFragment() {

    @Inject
    lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

//    @Inject
//    lateinit var productApiRepository: ProductApiRepository

    lateinit var viewModel : ListProductViewModel
//    private val viewModel : ListProductViewModel by viewModels()

//    private val viewModel: ListProductViewModel by viewModels {
//        InjectorUtils.provideProductListViewModelFactory(this)
//    }

    private var timer : Timer? = null
    private val DELAY: Long = 500 // in ms

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentListProductsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = ProductAdapter()
        binding.productList.adapter = adapter


        addEditextWatcher(binding)

        val layoutManager = binding.productList.layoutManager


        val customScrollListener = CustomScrollListener({
            nextPage()
        }, layoutManager as LinearLayoutManager)

        binding.productList.addOnScrollListener(customScrollListener)

        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }

        val factory = abstractViewModelFactory.create(this)
        viewModel = ViewModelProviders.of(this, factory).get(ListProductViewModel::class.java)
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListProductViewModel::class.java)

        subscribeUi(adapter, binding, customScrollListener)

        return binding.root
    }

    private fun addEditextWatcher(binding : FragmentListProductsBinding) {
        binding.edittextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        // TODO: do what you need here (refresh list)
                        // you will probably need to use
                        // runOnUiThread(Runnable action) for some specific
                        // actions

                        requireActivity().runOnUiThread(java.lang.Runnable {
                            updateQuery(s.toString())
                        })

                    }

                }, DELAY)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(timer != null) {
                    try {
                        timer!!.cancel()
                    } catch (ex : Exception) {

                    } finally {
                        timer = null
                    }

                }
            }
        })
    }

    private fun subscribeUi(adapter: ProductAdapter, binding : FragmentListProductsBinding, customScrollListener : CustomScrollListener) {

        viewModel.products.observe(viewLifecycleOwner, Observer {


            if (it.isSuccess()) {
                adapter.addDatas(it.getResultData(), viewModel.isRefresgState())
            } else {
                when(it.ex){
                    is UnknownHostException -> Snackbar.make(requireView(), R.string.network_error, Snackbar.LENGTH_LONG)
                                .show()
                    else -> Snackbar.make(requireView(), R.string.server_error, Snackbar.LENGTH_LONG)
                            .show()
                }
            }
            viewModel.setLoadingState(ListProductViewModel.COMPLETE)
        })

        viewModel.loadingDataState.observe(viewLifecycleOwner, Observer {
            when(it) {
                ListProductViewModel.LOADING_REFRESH -> {
                    customScrollListener.reset()
                    binding.refreshLayout.isRefreshing = true
                }
                else -> binding.refreshLayout.isRefreshing = false
            }

        })

    }

    private fun updateQuery(q : String) {

        if(!NetworkUtil.checkInternetConnection(requireContext())) {
            Snackbar.make(requireView(), R.string.network_error, Snackbar.LENGTH_LONG)
                    .show()
            return
        }

        with(viewModel) {
            viewModel.setSearchRequest(q, 1)
        }
    }

    private fun nextPage() {
        if(!NetworkUtil.checkInternetConnection(requireContext())) {
            Snackbar.make(requireView(), R.string.network_error, Snackbar.LENGTH_LONG)
                    .show()
            return
        }
        with(viewModel) {
            viewModel.nextPage()
        }
    }

    private fun refresh() {
        if(!NetworkUtil.checkInternetConnection(requireContext())) {
            Snackbar.make(requireView(), R.string.network_error, Snackbar.LENGTH_LONG)
                    .show()
            return
        }
        with(viewModel) {
            viewModel.refreshData()
        }
    }

}