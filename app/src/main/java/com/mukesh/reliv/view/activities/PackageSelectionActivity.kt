package com.mukesh.reliv.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CustomAlertDialog
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.ActivityPackageSelectionBinding
import com.mukesh.reliv.model.PackageDetailsDO
import com.mukesh.reliv.model.UserDO
import com.mukesh.reliv.view.adapters.PackageAdapter
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

class PackageSelectionActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var mBinding: ActivityPackageSelectionBinding
    private lateinit var packageAdapter: PackageAdapter
    private lateinit var userDO: UserDO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPackageSelectionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        userDO = Preferences.getObjectFromPreference<UserDO>(Preferences.USER_DETAILS_DO)!!

        loadStaticData()
    }

    private fun loadStaticData() {
        val package1 = PackageDetailsDO(
            1,
            "Silver Package",
            100.0,
            "3 Months",
            "This description is for testing purpose",
            R.drawable.silver_package
        )
        val package2 = PackageDetailsDO(
            2,
            "Platinum Package",
            170.0,
            "6 Months",
            "This description is for testing purpose",
            R.drawable.platinum_package
        )

        val arrPackages = listOf(package1, package2)

        mBinding.rvPackages.layoutManager = LinearLayoutManager(this)
        packageAdapter = PackageAdapter(this)
        mBinding.rvPackages.adapter = packageAdapter
        packageAdapter.refreshList(arrPackages)

    }

    fun proceedToPayment(sAmount: String, packageName: String) {

        // rounding off the amount.
        val amount = (sAmount.toFloat() * 100).roundToInt()

        // initialize Razorpay account.
        val checkout = Checkout()

        // set your id as below
        checkout.setKeyID(getString(R.string.razorpay_key_id))

        // set image
        checkout.setImage(R.drawable.common_full_open_on_phone)

        // initialize json object
        val jsonObject = JSONObject()
        try {
            // to put name
            jsonObject.put("name", "${userDO.Pd_First_Name} ${userDO.Pd_Last_Name}")

            // put description
            jsonObject.put("description", packageName)

            // to set theme color
            jsonObject.put("theme.color", "")

            // put the currency
            jsonObject.put("currency", "INR")

            // put amount
            jsonObject.put("amount", amount)

            // put mobile number
            jsonObject.put("prefill.contact", userDO.Pd_Mobile_Number)

            // put email
            jsonObject.put("prefill.email", userDO.Pd_Email)

            // open razorpay to checkout activity
            checkout.open(this@PackageSelectionActivity, jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        CustomAlertDialog.showDialog(
            this,
            getString(R.string.success),
            getString(R.string.payment_success),
            getString(R.string.ok),
            "",
            "payment_success",
            false
        )
        Preferences.saveBooleanInPreference(Preferences.IS_PAYMENT_DONE, true)
        val doctor = UserDO(
            "0", "Doctor", "XXX", "",
            "9999999999", "Male", "03 Feb 1987",
            "1.4 M", "70 KG", "", "05:00PM - 06:00PM"
        )
        Preferences.saveBooleanInPreference(Preferences.IS_PAYMENT_DONE, true)
        Preferences.saveObjectInPreference(Preferences.DOCTOR_OBJECT, doctor)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        CustomAlertDialog.showDialog(
            this,
            getString(R.string.failure),
            getString(R.string.payment_failure),
            getString(R.string.ok),
            "",
            "payment_failure",
            true
        )
        Preferences.saveBooleanInPreference(Preferences.IS_PAYMENT_DONE, false)
    }
}