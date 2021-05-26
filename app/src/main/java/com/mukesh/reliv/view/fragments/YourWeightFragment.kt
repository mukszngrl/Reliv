package com.mukesh.reliv.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.FragmentYourWeightBinding
import com.mukesh.reliv.view.activities.SignUpActivity

class YourWeightFragment : Fragment() {

    private lateinit var fragBinding: FragmentYourWeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.light_green)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentYourWeightBinding.inflate(layoutInflater)

        fragBinding.weightPicker.selectValue(55)

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        fragBinding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_yourWeight_to_dateOfBirth)
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