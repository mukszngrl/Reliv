package com.mukesh.reliv.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.FragmentYourWeightBinding
import com.mukesh.reliv.model.SignUpDO
import com.mukesh.reliv.view.activities.SignUpActivity

class YourWeightFragment : Fragment() {

    private lateinit var fragBinding: FragmentYourWeightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.light_green)
        }

        fragBinding = FragmentYourWeightBinding.inflate(layoutInflater)

        val signUpDOTemp = arguments?.getSerializable("SignUpDO") as SignUpDO

        if (signUpDOTemp.gender.equals("Male", ignoreCase = true))
            fragBinding.ivPerson.setImageResource(R.drawable.male_green)
        else
            fragBinding.ivPerson.setImageResource(R.drawable.female)

        fragBinding.weightPicker.selectValue(55)

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        fragBinding.btnNext.setOnClickListener {
            val signUpDO = SignUpDO(
                mobileNo = signUpDOTemp.mobileNo,
                firstName = signUpDOTemp.firstName,
                lastName = signUpDOTemp.lastName,
                profileImagePath = signUpDOTemp.profileImagePath,
                gender = signUpDOTemp.gender,
                height = signUpDOTemp.height,
                weight = fragBinding.tvSelectedWeight.text.toString()
            )
            val bundle = bundleOf(
                "SignUpDO" to signUpDO
            )
            findNavController().navigate(R.id.action_yourWeight_to_dateOfBirth, bundle)
        }

        fragBinding.weightPicker.setValuePickerListener(object : RulerValuePickerListener {
            @SuppressLint("SetTextI18n")
            override fun onValueChange(value: Int) {
                fragBinding.tvSelectedWeight.text = value.toString() + " " + getString(R.string.kg)
            }

            @SuppressLint("SetTextI18n")
            override fun onIntermediateValueChange(selectedValue: Int) {
                fragBinding.tvSelectedWeight.text =
                    selectedValue.toString() + " " + getString(R.string.kg)
            }
        })
        return fragBinding.root
    }
}