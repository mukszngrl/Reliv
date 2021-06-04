package com.mukesh.reliv.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.mukesh.reliv.R
import com.mukesh.reliv.view.activities.PackageSelectionActivity
import java.util.*

object CustomAlertDialog {

    private var listener: AlertButtonActionListener? = null

    fun showDialog(
        context: Context, strTitle: String, strMessage: String, strFirstBtn: String,
        strSecondBtn: String, from: String, isCancelable: Boolean
    ) {
        val dialog = Dialog(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)

        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        val llPositive = view.findViewById<LinearLayout>(R.id.ll_positive)
        val btnPositive = view.findViewById<Button>(R.id.btn_positive)
        val llNegative = view.findViewById<LinearLayout>(R.id.ll_negative)
        val btnNegative = view.findViewById<Button>(R.id.btn_negative)

        if (strFirstBtn.isEmpty()) {
            llPositive.visibility = View.GONE
        } else {
            llPositive.visibility = View.VISIBLE
            btnPositive.text = strFirstBtn
        }

        if (strSecondBtn.isEmpty()) {
            llNegative.visibility = View.GONE
        } else {
            llNegative.visibility = View.VISIBLE
            btnNegative.text = strSecondBtn
        }

        tvTitle.text = strTitle
        tvMessage.text = strMessage

        btnPositive.setOnClickListener {
            if (from.equals("payment_success", ignoreCase = true)) {
                if (context is PackageSelectionActivity)
                    context.finish()
            }
            dialog.dismiss()
        }

        btnNegative.setOnClickListener { dialog.dismiss() }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Objects.requireNonNull(dialog.window)!!
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(view)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCancelable)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.statusBarColor = context.resources.getColor(R.color.transparent)
        if (!(context as Activity).isFinishing) dialog.show()
    }

    fun setListener(listener: AlertButtonActionListener) {
        CustomAlertDialog.listener = listener
    }

    interface AlertButtonActionListener {
        fun yesButtonClicked(from: String?)
        fun noButtonClicked(from: String?)
    }
}