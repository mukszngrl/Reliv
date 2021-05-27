package com.mukesh.reliv.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.FragmentGenderSelectionBinding
import com.mukesh.reliv.model.SignUpDO
import com.mukesh.reliv.view.activities.SignUpActivity


class GenderSelectionFragment : Fragment() {

    private lateinit var fragBinding: FragmentGenderSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_blue)
        }

        fragBinding = FragmentGenderSelectionBinding.inflate(layoutInflater)

        val signUpDOTemp = arguments?.getSerializable("SignUpDO") as SignUpDO

        fragBinding.clMale.setOnClickListener {
            val signUpDO = SignUpDO(
                mobileNo = signUpDOTemp.mobileNo,
                firstName = signUpDOTemp.firstName,
                lastName = signUpDOTemp.lastName,
                profileImagePath = signUpDOTemp.profileImagePath,
                gender = "Male"
            )
            val bundle = bundleOf(
                "SignUpDO" to signUpDO
            )
            findNavController().navigate(R.id.action_genderSelection_to_yourHeight, bundle)
        }

        fragBinding.clFemale.setOnClickListener {
            val signUpDO = SignUpDO(
                mobileNo = signUpDOTemp.mobileNo,
                firstName = signUpDOTemp.firstName,
                lastName = signUpDOTemp.lastName,
                profileImagePath = signUpDOTemp.profileImagePath,
                gender = "Female"
            )
            val bundle = bundleOf(
                "SignUpDO" to signUpDO
            )
            findNavController().navigate(R.id.action_genderSelection_to_yourHeight, bundle)
        }

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return fragBinding.root
    }

}