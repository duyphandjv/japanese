package com.phanduy.tekotest.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.phanduy.tekotest.R
import com.phanduy.tekotest.adapters.JapaneseWordAdapter
import com.phanduy.tekotest.appComponent
import com.phanduy.tekotest.databinding.FragmentGamingBinding
import com.phanduy.tekotest.di.InjectingSavedStateViewModelFactory
import com.phanduy.tekotest.viewmodel.GamingViewModel
import javax.inject.Inject

class GamingFragment : Fragment() {

    @Inject
    lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

    lateinit var gamingViewModel: GamingViewModel

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = abstractViewModelFactory.createGamingFactory(this)
        gamingViewModel = ViewModelProviders.of(this, factory).get(GamingViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentGamingBinding>(
            inflater, R.layout.fragment_gaming, container, false
        ).apply {

            viewModel = gamingViewModel
            lifecycleOwner = viewLifecycleOwner

            callback = object : Callback {
                override fun onBack() {
                    view!!.findNavController().navigateUp()
                }

                override fun onShow() {
                }

                override fun onRefresh() {
                    gamingViewModel.refresh()
                }
            }

            val numberOfColumns = 6
            listWord.layoutManager = GridLayoutManager(context, numberOfColumns)

            val adapter = JapaneseWordAdapter()
            listWord.adapter = adapter

            subscribeUi(adapter, this)
        }
        return binding.root
    }

    private fun subscribeUi(adapter: JapaneseWordAdapter, binding : FragmentGamingBinding) {
        gamingViewModel.words.observe(viewLifecycleOwner, Observer {
            adapter.addDatas(it, true)
        })
    }

    interface Callback {
        fun onBack()
        fun onShow()
        fun onRefresh()
    }

}