package com.mukesh.reliv.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mesibo.api.Mesibo
import com.mesibo.messaging.MesiboUI
import com.mukesh.reliv.R
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.ActivityDashboardBinding
import com.mukesh.reliv.model.UserDO
import com.mukesh.reliv.viewmodel.DashboardActivityViewModel

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        /*CustomLoader.showLoader(this)
        dashboardViewModel.getMesiboUserToken("")!!.observe(this, { mesiboUserTokenResponse ->
            CustomLoader.hideLoader()
            mesiboUserTokenResponse.let {
                Preferences.saveObjectInPreference(
                    Preferences.MESIBO_USER_TOKEN_RESPONSE_OBJECT,
                    mesiboUserTokenResponse
                )
                Preferences.saveStringInPreference(
                    Preferences.MESIBO_USER_TOKEN,

                    mesiboUserTokenResponse.user.token
                )
            }
        })*/
        mBinding.ivCholestrol.setOnClickListener(this)
        mBinding.ivObesity.setOnClickListener(this)
        mBinding.ivThyroid.setOnClickListener(this)
        mBinding.tvBookedSlot.setOnClickListener(this)

        setTitleBar()
        initialiseMesibo()
    }

    private fun initialiseMesibo() {
        val api: Mesibo = Mesibo.getInstance()
        api.init(applicationContext)

        Mesibo.addListener(this)
        Mesibo.setSecureConnection(true)
//        Mesibo.setAccessToken("651e776e8a049268d2499ddcf737a939d4a92f92c047eecc32ef84")
        Mesibo.setAccessToken("ccbf0f8b457746734ca06c9af036e3ddc50a239d17123ce6632ec8b")
        Mesibo.setDatabase("mydb", 0)
        Mesibo.start()
    }

    private fun setTitleBar() {
        // calling the action bar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#473E97")))
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.elevation = 0F;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#473E97")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        if (Preferences.getObjectFromPreference<UserDO>(Preferences.DOCTOR_OBJECT) != null) {
            val userDO = Preferences.getObjectFromPreference<UserDO>(Preferences.DOCTOR_OBJECT)
            mBinding.tvBookedSlot.text =
                "Your slot are booked with ${userDO?.Pd_First_Name} ${userDO?.Pd_Last_Name} at ${userDO?.SchedulingTimes}"
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_obesity, R.id.iv_cholestrol, R.id.iv_thyroid -> {
                val intent = Intent(this, PackageSelectionActivity::class.java)
                when (p0.id) {
                    R.id.iv_obesity -> intent.putExtra("FROM", "Obesity")
                    R.id.iv_cholestrol -> intent.putExtra("FROM", "Cholesterol")
                    else -> intent.putExtra("FROM", "Thyroid")
                }
                startActivity(intent)
            }
            R.id.tv_booked_slot -> {
//                if (mBinding.tvBookedSlot.text.toString() != getString(R.string.doctor_slot_not_booked_yet)) {
                onLaunchMessagingUi()
//                }
            }
        }
    }

    private fun onLaunchMessagingUi() {
        val mProfile = Mesibo.UserProfile()
        mProfile.address = "7569858421"
        mProfile.name = "Vipin"
        Mesibo.setUserProfile(mProfile, false)
/*
        val mProfile = Mesibo.UserProfile()
        mProfile.address = "9405986565"
        mProfile.name = "Mukesh"
        Mesibo.setUserProfile(mProfile, false)
*/
//        MesiboUI.launchMessageView(this, mProfile.address, 0)

        val intent = Intent(this, MessageViewActivity::class.java)
        intent.putExtra("address", mProfile.address)
        intent.putExtra("name", mProfile.name)
        startActivity(intent)
    }
}