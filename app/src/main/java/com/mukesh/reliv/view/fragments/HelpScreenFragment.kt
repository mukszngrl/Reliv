package com.mukesh.reliv.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.HelpScreenFragmentBinding


class HelpScreenFragment : Fragment() {
    private val ARG_OBJECT = "object"
    private lateinit var fBinding: HelpScreenFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fBinding = HelpScreenFragmentBinding.inflate(layoutInflater, container, false)
        return fBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            when (getInt(ARG_OBJECT)) {
                0 -> {
                    fBinding.tvTitle1.text = getString(R.string.life_isn_t_about)
                    fBinding.tvTitle2.text = getString(R.string.finding_yourself)
                    fBinding.tvTitle3.text = getString(R.string.life_is_about_creating_yourself)
                }
                1 -> {
                    fBinding.tvTitle1.text = getString(R.string.your_can_create)
                    fBinding.tvTitle2.text = getString(R.string.your_life)
                    fBinding.tvTitle3.text = getString(R.string.however_you_wish_it_to_be)
                }
                2 -> {
                    fBinding.tvTitle1.text = getString(R.string.there_is_nothing)
                    fBinding.tvTitle2.text = getString(R.string.either_good_or_bad)
                    fBinding.tvTitle3.text = getString(R.string.but_thinking_makes_it_so)
                }
            }
        }
    }

}
