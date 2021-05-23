package com.mukesh.reliv.view.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.ActivityPaymentBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt


class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var mBinding: ActivityPaymentBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        context = this@PaymentActivity

        // adding on click listener to our button.
        mBinding.idBtnPay.setOnClickListener {
            // on below line we are getting
            // amount that is entered by user.
            val sAmount = mBinding.idEdtAmount.text.toString()

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
                jsonObject.put("name", "Mukesh")

                // put description
                jsonObject.put("description", "Test payment")

                // to set theme color
                jsonObject.put("theme.color", "")

                // put the currency
                jsonObject.put("currency", "INR")

                // put amount
                jsonObject.put("amount", amount)

                // put mobile number
                jsonObject.put("prefill.contact", "9405986565")

                // put email
                jsonObject.put("prefill.email", "muks.zingare91@gmail.com")

                // open razorpay to checkout activity
                checkout.open(context as PaymentActivity, jsonObject)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(context, "Payment Success...", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(context, "Payment Failure...", Toast.LENGTH_SHORT).show()
    }
}