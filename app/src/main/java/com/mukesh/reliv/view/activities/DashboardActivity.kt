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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesibo.api.Mesibo
import com.mesibo.calls.api.MesiboCall
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CustomAlertDialog
import com.mukesh.reliv.common.CustomLoader
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.ActivityDashboardBinding
import com.mukesh.reliv.model.*
import com.mukesh.reliv.retrofit.RetrofitClient
import com.mukesh.reliv.view.adapters.SchedulingAdapter
import com.mukesh.reliv.viewmodel.DashboardActivityViewModel
import retrofit2.Response

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardActivityViewModel
    private lateinit var scheduleAdapter: SchedulingAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        dashboardViewModel = ViewModelProvider(this).get(DashboardActivityViewModel::class.java)
        scheduleAdapter = SchedulingAdapter(this)
        mBinding.rvBookedSlot.layoutManager = LinearLayoutManager(this)
        mBinding.rvBookedSlot.adapter = scheduleAdapter

        if (Preferences.getStringFromPreference(Preferences.LAST_MESIBO_USER, "") == ""
            || Preferences.getStringFromPreference(
                Preferences.LAST_MESIBO_USER,
                ""
            ) != Preferences.getStringFromPreference(Preferences.MOBILE_NO, "")
        ) {
            dashboardViewModel.getMesiboUserToken(
                Preferences.getStringFromPreference(
                    Preferences.MOBILE_NO,
                    ""
                )!!
            )!!
                .observe(this, { mesiboUserTokenResponse ->
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
                        initialiseMesibo()
                        Preferences.saveStringInPreference(
                            Preferences.LAST_MESIBO_USER,
                            Preferences.getStringFromPreference(Preferences.MOBILE_NO, "")!!
                        )
                    }
                })
        } else {
            initialiseMesibo()
        }

        CustomLoader.showLoader(this)
        getScheduling()
        mBinding.ivCholestrol.setOnClickListener(this)
        mBinding.ivObesity.setOnClickListener(this)
        mBinding.ivThyroid.setOnClickListener(this)

        mBinding.swipeRefresh.setOnRefreshListener {
            getScheduling()
        }

        setTitleBar()

        if (Preferences.getStringFromPreference(Preferences.USER_TYPE, "") == "Doctor") {
            mBinding.llDisease.visibility = View.GONE
            mBinding.llDoctor.visibility = View.VISIBLE
            mBinding.ivConsult.visibility = View.INVISIBLE
            val docDetails: PatientScheduleTimingsDO =
                Preferences.getObjectFromPreference<PatientScheduleTimingsDO>(Preferences.USER_DETAILS_DO)!!
            mBinding.tvDoctorName.text = "${docDetails.Dd_Prefix} ${docDetails.Dd_Name}"

        } else {
            mBinding.llDisease.visibility = View.VISIBLE
            mBinding.llDoctor.visibility = View.GONE
            mBinding.ivConsult.visibility = View.VISIBLE
        }
    }

    private fun initialiseMesibo() {
        val api: Mesibo = Mesibo.getInstance()
        api.init(applicationContext)

        Mesibo.addListener(this)
        Mesibo.setSecureConnection(true)
        Mesibo.setAccessToken(
            Preferences.getStringFromPreference(
                Preferences.MESIBO_USER_TOKEN, ""
            )
        )
        Mesibo.setDatabase("mydb", 0)
        Mesibo.start()

        MesiboCall.getInstance().init(applicationContext)
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
        }
    }

    fun onLaunchMessagingUi(
        patientSchedule: PatientScheduleTimingsDO?,
        doctorSchedule: DoctorScheduleTimingsDO?
    ) {
        val mProfile = Mesibo.UserProfile()
        val intent = Intent(this, MessageViewActivity::class.java)
        if (patientSchedule != null) {
            mProfile.address = patientSchedule.Dd_Phone
            mProfile.name = "${patientSchedule.Dd_Prefix} ${patientSchedule.Dd_Name}"
            intent.putExtra("designation", patientSchedule.Dd_Designation)
        } else {
            mProfile.address = doctorSchedule?.Pd_Mobile_Number
            mProfile.name = "${doctorSchedule?.Pd_First_Name} ${doctorSchedule?.Pd_Last_Name}"
            intent.putExtra("designation", "")
        }
        /*if (Preferences.getStringFromPreference(Preferences.USER_TYPE, "") == "Doctor") {
            mProfile.address = "8888888888"
            mProfile.name = "Patient"
        } else {
            mProfile.address = "9999999999"
            mProfile.name = "Doctor"
        }*/
        Mesibo.setUserProfile(mProfile, false)

//        MesiboUI.launchMessageView(this, mProfile.address, 0)


        intent.putExtra("address", mProfile.address)
        intent.putExtra("name", mProfile.name)
        startActivity(intent)
    }

    private fun getScheduling() {
        Thread {
            val call =
                RetrofitClient.apiInterface.getScheduledDetails(RetrofitClient.getHeaders())
            val response: Response<GetScheduleDetailsResponse> = call.execute()
            val scheduleResponse: GetScheduleDetailsResponse? = response.body()
            CustomLoader.hideLoader()
            mBinding.swipeRefresh.isRefreshing = false

            try {
                runOnUiThread {
                    scheduleResponse.let {
                        if (scheduleResponse?.statusCode == 200) {
                            if (Preferences.getStringFromPreference(Preferences.USER_TYPE, "")
                                    .equals("Patient")
                            ) {
                                if (scheduleResponse.Data.PatientSchedulingTimings != null) {
                                    mBinding.rvBookedSlot.visibility = View.VISIBLE
                                    mBinding.cvChat.visibility = View.GONE
                                    scheduleAdapter.refresh(
                                        scheduleResponse.Data.DoctorSchedulingTimings,
                                        scheduleResponse.Data.PatientSchedulingTimings
                                    )
                                } else {
                                    mBinding.rvBookedSlot.visibility = View.GONE
                                    mBinding.cvChat.visibility = View.VISIBLE
                                }
                            } else {
                                if (scheduleResponse.Data.DoctorSchedulingTimings != null) {
                                    mBinding.rvBookedSlot.visibility = View.VISIBLE
                                    mBinding.cvChat.visibility = View.GONE
                                    scheduleAdapter.refresh(
                                        scheduleResponse.Data.DoctorSchedulingTimings,
                                        scheduleResponse.Data.PatientSchedulingTimings
                                    )
                                } else {
                                    mBinding.rvBookedSlot.visibility = View.GONE
                                    mBinding.cvChat.visibility = View.VISIBLE
                                }
                            }
                        } else {
                            mBinding.rvBookedSlot.visibility = View.GONE
                            mBinding.cvChat.visibility = View.VISIBLE
                        }
                    }
                }
            } catch (ex: Exception) {
                CustomLoader.hideLoader()
                CustomAlertDialog.showDialog(
                    this@DashboardActivity, getString(R.string.alert),
                    ex.printStackTrace().toString(),
                    getString(R.string.ok), "", "", true
                )
            }
        }.start()
        /*dashboardViewModel.getScheduledDetails()!!.observe(this, { scheduleResponse ->

        })*/
    }
}