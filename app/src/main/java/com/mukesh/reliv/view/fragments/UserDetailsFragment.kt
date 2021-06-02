package com.mukesh.reliv.view.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CustomAlertDialog
import com.mukesh.reliv.databinding.FragmentUserDetailsBinding
import com.mukesh.reliv.model.SignUpDO
import com.mukesh.reliv.view.activities.SignUpActivity

class UserDetailsFragment : Fragment() {

    private lateinit var fragBinding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.WHITE
        }
        fragBinding = FragmentUserDetailsBinding.inflate(layoutInflater)

        val mobileNo = arguments?.get("MobileNo") as String

        fragBinding.btnNext.setOnClickListener {
            val firstName = fragBinding.etFirstName.text.toString()
            val lastName = fragBinding.etLastName.text.toString()
            when {
                firstName.isEmpty() ->
                    CustomAlertDialog.showDialog(
                        requireActivity(),
                        getString(R.string.alert),
                        getString(R.string.please_enter_first_name),
                        getString(R.string.ok),
                        "",
                        "",
                        true
                    )
                lastName.isEmpty() ->
                    CustomAlertDialog.showDialog(
                        requireActivity(),
                        getString(R.string.alert),
                        getString(R.string.please_enter_last_name),
                        getString(R.string.ok),
                        "",
                        "",
                        true
                    )
                else -> {
                    val signUpDO =
                        SignUpDO(mobileNo = mobileNo, firstName = firstName, lastName = lastName)
                    val bundle = bundleOf(
                        "SignUpDO" to signUpDO
                    )
                    findNavController().navigate(R.id.action_userDetails_to_takePhoto, bundle)
                }
            }
        }

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return fragBinding.root
    }
}