package com.phanduy.tekotest.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.phanduy.tekotest.R
import com.phanduy.tekotest.appComponent
import com.phanduy.tekotest.databinding.FragmentLandingPageBinding

class LandingPageFragment : Fragment() {

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLandingPageBinding>(
            inflater, R.layout.fragment_landing_page, container, false
        ).apply {

            lifecycleOwner = viewLifecycleOwner

            callback = object : Callback {

                override fun goToLearningHiraganaAlphabet() {
                    // view.findNavController().navigate(action_landing_page_to_learning_fragment)
                    val direction =
                        LandingPageFragmentDirections.actionLandingPageToLearningFragment()
                    findNavController().navigate(direction)
                }

                override fun goToLearningKatakanaAlphabet() {
                    val direction =
                        LandingPageFragmentDirections.actionLandingPageToKatakanaFragment()
                    findNavController().navigate(direction)
                }

                override fun goToGaming() {
                    val direction =
                        LandingPageFragmentDirections.actionLandingPageToGamingFragment()
                    findNavController().navigate(direction)
                }
            }
        }

        return binding.root
    }

    interface Callback {
        fun goToLearningHiraganaAlphabet()
        fun goToLearningKatakanaAlphabet()
        fun goToGaming()
    }
}