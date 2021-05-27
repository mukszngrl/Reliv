package com.mukesh.reliv.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CalendarUtils
import com.mukesh.reliv.common.CustomAlertDialog
import com.mukesh.reliv.databinding.FragmentDateOfBirthBinding
import com.mukesh.reliv.model.SignUpDO
import com.mukesh.reliv.view.activities.SignUpActivity

class DateOfBirthFragment : Fragment() {

    private lateinit var fragBinding: FragmentDateOfBirthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentDateOfBirthBinding.inflate(layoutInflater)

        val signUpDOTemp = arguments?.getSerializable("SignUpDO") as SignUpDO

        val day = CalendarUtils.getCurrentDateToFormat(CalendarUtils.DD_PATTERN)
        val month = CalendarUtils.getCurrentDateToFormat(CalendarUtils.MM_PATTERN)
        val year = CalendarUtils.getCurrentDateToFormat(CalendarUtils.YYYY_PATTERN)
        fragBinding.monthPicker.minValue = 0
        fragBinding.monthPicker.maxValue = 11
        fragBinding.monthPicker.value = (month?.toInt() ?: 0) - 1
        val arrMonths = resources.getStringArray(R.array.month_list)
        fragBinding.monthPicker.displayedValues = arrMonths

        fragBinding.dayPicker.minValue = 0
        fragBinding.dayPicker.maxValue = 30
        fragBinding.dayPicker.value = (day?.toInt() ?: 1) - 1
        val arrDays = resources.getStringArray(R.array.day_list)
        fragBinding.dayPicker.displayedValues = arrDays

        fragBinding.yearPicker.minValue = 1950
        fragBinding.yearPicker.maxValue = year?.toInt() ?: 2021
        fragBinding.yearPicker.value = year?.toInt() ?: 2021

        val defaultDOB =
            arrDays[fragBinding.dayPicker.value] + "-" + arrMonths[fragBinding.monthPicker.value] + "-" + fragBinding.yearPicker.value

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        fragBinding.btnNext.setOnClickListener {
            val dob =
                arrDays[fragBinding.dayPicker.value] + "-" + arrMonths[fragBinding.monthPicker.value] + "-" + fragBinding.yearPicker.value

            when {
                dob.isEmpty() || dob == defaultDOB -> CustomAlertDialog.showDialog(
                    requireActivity(),
                    getString(R.string.alert),
                    getString(R.string.please_select_your_date_of_birth),
                    getString(R.string.ok),
                    "",
                    "",
                    true
                )
                else -> {
                    val signUpDO = SignUpDO(
                        mobileNo = signUpDOTemp.mobileNo,
                        firstName = signUpDOTemp.firstName,
                        lastName = signUpDOTemp.lastName,
                        profileImagePath = signUpDOTemp.profileImagePath,
                        gender = signUpDOTemp.gender,
                        height = signUpDOTemp.height,
                        weight = signUpDOTemp.weight,
                        dateOfBirth = arrDays[fragBinding.dayPicker.value] + "-" + arrMonths[fragBinding.monthPicker.value] + "-" + fragBinding.yearPicker.value
                    )
                    val bundle = bundleOf(
                        "SignUpDO" to signUpDO
                    )
                    findNavController().navigate(R.id.action_dateOfBirth_to_yourLocation, bundle)
                }
            }
        }

        return fragBinding.root
    }
}