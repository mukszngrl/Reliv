package com.mukesh.reliv.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.FragmentYourHeightBinding
import com.mukesh.reliv.view.activities.SignUpActivity

class YourHeightFragment : Fragment(), View.OnClickListener {

    private lateinit var fragBinding: FragmentYourHeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_blue)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentYourHeightBinding.inflate(layoutInflater)

        removeSelection()
        fragBinding.tv14m.textSize = 19F
        fragBinding.tv14m.setTextColor(resources.getColor(R.color.black))
        fragBinding.view14m.setBackgroundColor(resources.getColor(R.color.black))
        fragBinding.tvSelectedHeight.text = fragBinding.tv14m.text.toString()

        fragBinding.ll10m.setOnClickListener(this)
        fragBinding.ll11m.setOnClickListener(this)
        fragBinding.ll12m.setOnClickListener(this)
        fragBinding.ll13m.setOnClickListener(this)
        fragBinding.ll14m.setOnClickListener(this)
        fragBinding.ll15m.setOnClickListener(this)
        fragBinding.ll16m.setOnClickListener(this)
        fragBinding.ll17m.setOnClickListener(this)
        fragBinding.ll18m.setOnClickListener(this)
        fragBinding.ll19m.setOnClickListener(this)
        fragBinding.ll20m.setOnClickListener(this)
        fragBinding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_yourHeight_to_yourWeight)
        }
        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return fragBinding.root
    }

    private fun removeSelection() {
        fragBinding.tv10m.textSize = 15F
        fragBinding.tv10m.setTextColor(resources.getColor(R.color.white))
        fragBinding.tv11m.textSize = 15F
        fragBinding.tv11m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view11m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv12m.textSize = 15F
        fragBinding.tv12m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view12m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv13m.textSize = 15F
        fragBinding.tv13m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view13m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv14m.textSize = 15F
        fragBinding.tv14m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view14m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv15m.textSize = 15F
        fragBinding.tv15m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view15m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv16m.textSize = 15F
        fragBinding.tv16m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view16m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv17m.textSize = 15F
        fragBinding.tv17m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view17m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv18m.textSize = 15F
        fragBinding.tv18m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view18m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv19m.textSize = 15F
        fragBinding.tv19m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view19m.setBackgroundColor(resources.getColor(R.color.white))
        fragBinding.tv20m.textSize = 15F
        fragBinding.tv20m.setTextColor(resources.getColor(R.color.white))
        fragBinding.view20m.setBackgroundColor(resources.getColor(R.color.white))
    }

    override fun onClick(view: View?) {
        removeSelection()
        when (view?.id) {
            fragBinding.ll10m.id -> {
                fragBinding.tv10m.textSize = 19F
                fragBinding.tv10m.setTextColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv10m.text.toString()
            }
            fragBinding.ll11m.id -> {
                fragBinding.tv11m.textSize = 19F
                fragBinding.tv11m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view11m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv11m.text.toString()
            }
            fragBinding.ll12m.id -> {
                fragBinding.tv12m.textSize = 19F
                fragBinding.tv12m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view12m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv12m.text.toString()
            }
            fragBinding.ll13m.id -> {
                fragBinding.tv13m.textSize = 19F
                fragBinding.tv13m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view13m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv13m.text.toString()
            }
            fragBinding.ll14m.id -> {
                fragBinding.tv14m.textSize = 19F
                fragBinding.tv14m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view14m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv14m.text.toString()
            }
            fragBinding.ll15m.id -> {
                fragBinding.tv15m.textSize = 19F
                fragBinding.tv15m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view15m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv15m.text.toString()
            }
            fragBinding.ll16m.id -> {
                fragBinding.tv16m.textSize = 19F
                fragBinding.tv16m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view16m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv16m.text.toString()
            }
            fragBinding.ll17m.id -> {
                fragBinding.tv17m.textSize = 19F
                fragBinding.tv17m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view17m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv17m.text.toString()
            }
            fragBinding.ll18m.id -> {
                fragBinding.tv18m.textSize = 19F
                fragBinding.tv18m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view18m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv18m.text.toString()
            }
            fragBinding.ll19m.id -> {
                fragBinding.tv19m.textSize = 19F
                fragBinding.tv19m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view19m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv19m.text.toString()
            }
            fragBinding.ll20m.id -> {
                fragBinding.tv20m.textSize = 19F
                fragBinding.tv20m.setTextColor(resources.getColor(R.color.black))
                fragBinding.view20m.setBackgroundColor(resources.getColor(R.color.black))
                fragBinding.tvSelectedHeight.text = fragBinding.tv20m.text.toString()
            }
        }
    }
}