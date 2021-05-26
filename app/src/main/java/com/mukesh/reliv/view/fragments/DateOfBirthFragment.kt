package com.mukesh.reliv.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CalendarUtils
import com.mukesh.reliv.databinding.FragmentDateOfBirthBinding
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

        fragBinding.tvSelectValue.text =
            "${arrDays[(day?.toInt() ?: 1) - 1]} - ${arrMonths[(month?.toInt() ?: 1)]} - ${(year?.toInt() ?: 2021)}"

        fragBinding.monthPicker.setOnValueChangedListener { _, oldValue, newValue ->
            if (fragBinding.tvSelectValue.text.toString().contains(arrMonths[oldValue]))
                fragBinding.tvSelectValue.text =
                    "${arrDays[fragBinding.dayPicker.value]} - ${arrMonths[newValue]} - ${fragBinding.yearPicker.value}"
        }

        fragBinding.dayPicker.setOnValueChangedListener { _, oldValue, newValue ->

            if (fragBinding.tvSelectValue.text.toString().contains("$oldValue"))
                fragBinding.tvSelectValue.text =
                    "${arrDays[newValue]} - ${arrMonths[fragBinding.monthPicker.value]} - ${fragBinding.yearPicker.value}"
        }

        fragBinding.yearPicker.setOnValueChangedListener { _, oldValue, newValue ->
            if (fragBinding.tvSelectValue.text.toString().contains("$oldValue"))
                fragBinding.tvSelectValue.text =
                    "${arrDays[fragBinding.dayPicker.value]} - ${arrMonths[fragBinding.monthPicker.value]} - $newValue"
        }

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return fragBinding.root
    }
}