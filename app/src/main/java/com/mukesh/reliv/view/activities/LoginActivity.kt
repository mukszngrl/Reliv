package com.mukesh.reliv.view.activities

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CustomAlertDialog
import com.mukesh.reliv.common.CustomLoader
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.ActivityLoginBinding
import com.mukesh.reliv.databinding.PopupOtpBinding
import com.mukesh.reliv.viewmodel.LoginActivityViewModel
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var otpBinding: PopupOtpBinding
    private lateinit var loginActivityViewModel: LoginActivityViewModel
    private lateinit var mobNo: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.very_gray_light_color)
        }

        loginActivityViewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

        if (mBinding.etUsername.text.toString() == "")
            mBinding.etUsername.requestFocus()

        mBinding.btnGetOtp.setOnClickListener {
            when {
                mBinding.etUsername.text.toString().trim() == "" -> Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.please_enter_username),
                    Toast.LENGTH_SHORT
                ).show()
                mBinding.etMobNo.text.toString().trim() == "" -> Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.please_enter_mobile_number),
                    Toast.LENGTH_SHORT
                ).show()
                mBinding.etMobNo.text.toString().trim().length != 10 -> Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.entered_mobile_number_length_should_be_10),
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    CustomLoader.showLoader(this)
                    name = mBinding.etUsername.text.toString().trim()
                    mobNo = mBinding.etMobNo.text.toString().trim()

                    loginActivityViewModel.generateUserOTP(name, mobNo)!!
                        .observe(this@LoginActivity, { generateOTPResponse ->
                            if (generateOTPResponse != null && generateOTPResponse.statusCode == 200) {
                                showOTPPopup()
                            } else {
                                CustomAlertDialog.showDialog(
                                    this@LoginActivity, getString(R.string.alert),
                                    getString(R.string.something_went_wrong),
                                    getString(R.string.ok), "", "", true
                                )
                            }
                        })
                }
            }
        }
    }

    private fun showOTPPopup() {
        val mCustomOTPDialog = Dialog(this)
        otpBinding = PopupOtpBinding.inflate(layoutInflater)
        mCustomOTPDialog.setContentView(otpBinding.root)
        otpBinding.otpView.requestFocus()

        otpBinding.otpView.otpListener(object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                mCustomOTPDialog.dismiss()
                loginActivityViewModel.validateUserOTP(mobNo, otp)!!
                    .observe(this@LoginActivity, { otpValidationResponse ->
                        if (otpValidationResponse != null && otpValidationResponse.statusCode == 200) {
                            otpValidationResponse.Data.Token.let {
                                Preferences.saveStringInPreference(
                                    Preferences.GUID_TOKEN,
                                    otpValidationResponse.Data.Token
                                )
                            }

                            if (!otpValidationResponse.Data.IsRegisterUser) {
                                val intent =
                                    Intent(this@LoginActivity, SignUpActivity::class.java)
                                intent.putExtra("MobileNo", mobNo)
                                startActivity(intent)
                            } else {
                                loginActivityViewModel.getUserDetails()!!
                                    .observe(this@LoginActivity, { userDetailsResponse ->
                                        if (userDetailsResponse != null && userDetailsResponse.statusCode == 200) {
                                            Preferences.saveObjectInPreference(
                                                Preferences.USER_DETAILS_DO,
                                                userDetailsResponse.Data
                                            )
                                            val intent =
                                                Intent(
                                                    this@LoginActivity,
                                                    SignUpActivity::class.java
                                                )
                                            intent.putExtra("MobileNo", mobNo)
                                            startActivity(intent)
                                        }
                                    })
                            }
                        }
                    })
            }
        })

        mCustomOTPDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mCustomOTPDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        mCustomOTPDialog.show()

        CustomLoader.hideLoader()
    }

    private val smsListener: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
                val bundle = intent.extras // ---get the SMS message
                // passed in---
                var msgs: Array<SmsMessage?>? = null
                // String msg_from;
                if (bundle != null) {
                    // ---retrieve the SMS message received---
                    try {
                        val pdus = bundle["pdus"] as Array<*>?
                        msgs = arrayOfNulls(pdus!!.size)
                        for (i in msgs.indices) {
                            msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                            // msg_from = msgs[i].getOriginatingAddress();
                            val msgBody: String = msgs[i]?.messageBody ?: ""
                            otpBinding.let {
                                otpBinding.otpView.let { otpBinding.otpView.setOTP(msgBody) }
                            }
                            // do your stuff
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        this@LoginActivity.unregisterReceiver(smsListener)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        this@LoginActivity.registerReceiver(smsListener, intentFilter)
    }

    private fun OtpTextView.otpListener(otpListener: OTPListener) {
        this.otpListener = otpListener
    }
}