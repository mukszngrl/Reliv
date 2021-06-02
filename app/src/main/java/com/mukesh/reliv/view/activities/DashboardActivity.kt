package com.mukesh.reliv.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mukesh.reliv.common.CustomLoader
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.ActivityDashboardBinding
import com.mukesh.reliv.viewmodel.DashboardActivityViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        CustomLoader.showLoader(this)
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
        })
    }
}