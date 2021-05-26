package com.mukesh.reliv.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.FragmentGenderSelectionBinding
import com.mukesh.reliv.view.activities.SignUpActivity


class GenderSelectionFragment : Fragment() {

    private lateinit var fragBinding: FragmentGenderSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_blue)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentGenderSelectionBinding.inflate(layoutInflater)
        fragBinding.clMale.setOnClickListener {
            findNavController().navigate(R.id.action_genderSelection_to_yourHeight)
        }

        fragBinding.clFemale.setOnClickListener {
            findNavController().navigate(R.id.action_genderSelection_to_yourHeight)
        }

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return fragBinding.root
    }

}