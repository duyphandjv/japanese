package com.phanduy.tekotest.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.phanduy.tekotest.R
import com.phanduy.tekotest.appComponent
import com.phanduy.tekotest.databinding.FragmentKatakanaBinding
import com.phanduy.tekotest.di.InjectingSavedStateViewModelFactory
import com.phanduy.tekotest.viewmodel.KatakanaViewModel
import javax.inject.Inject

class KatakanaFragment : Fragment() {

    @Inject
    lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

    lateinit var katakanaViewModel: KatakanaViewModel

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = abstractViewModelFactory.createKatakanaFactory(this)
        katakanaViewModel = ViewModelProviders.of(this, factory).get(KatakanaViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentKatakanaBinding>(
            inflater, R.layout.fragment_katakana, container, false
        ).apply {

            viewModel = katakanaViewModel
            lifecycleOwner = viewLifecycleOwner

            callback = object : Callback {
                override fun onBack() {
                    view!!.findNavController().navigateUp()
                }

                override fun onNext() {
                    viewModel!!.nextIndex()
                }

                override fun onRandom() {
                    viewModel!!.randomIndex()
                }

                override fun onShow() {
                    layoutCover.visibility = if(layoutCover.visibility == View.VISIBLE) View.INVISIBLE
                    else View.VISIBLE
                }
            }
            bindData(this)
            findImage("a", this)
            subscribeUi(this)
        }

        return binding.root
    }

    fun subscribeUi(binding: FragmentKatakanaBinding) {
        katakanaViewModel.currentWord.observe(viewLifecycleOwner, Observer {
            findImage(it, binding)
        })
    }

    private fun bindData(binding : FragmentKatakanaBinding) {
        ArrayAdapter.createFromResource(
            context,
            R.array.groups,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = it
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                katakanaViewModel.updateGroup(selectedItem)
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun findImage(txt: String, binding: FragmentKatakanaBinding) {
        binding.layoutCover.visibility = View.VISIBLE
        binding.image.setImageDrawable(getDrawable(txt + ".png"))
    }

    fun getDrawable(filename : String) : Drawable {
        return Drawable.createFromResourceStream(resources,
            TypedValue(), resources.getAssets().open(filename), null)
    }

    interface Callback {
        fun onBack()
        fun onNext()
        fun onRandom()
        fun onShow()
    }

}