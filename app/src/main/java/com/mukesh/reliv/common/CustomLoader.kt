package com.mukesh.reliv.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.mukesh.reliv.R
import ir.alirezabdn.wp7progress.WP10ProgressBar

object CustomLoader {
    private var mDialog: Dialog? = null
    private lateinit var mProgressBar: WP10ProgressBar

    @SuppressLint("SetTextI18n")
    fun showLoader(context: Context, message: String, cancelable: Boolean) {
        if (mDialog != null && mDialog?.isShowing == true) {
            mProgressBar.let { mProgressBar.hideProgressBar() }
            mDialog?.dismiss()
            mDialog = null
        }
        mDialog = Dialog(context, R.style.LoaderTheme)
        mDialog!!.setContentView(R.layout.prograss_bar_dialog)
        mProgressBar = mDialog!!.findViewById(R.id.wp7_progressBar)
        val progressText = mDialog!!.findViewById<TextView>(R.id.progress_text)
        progressText.text = "" + message
        mProgressBar.showProgressBar()
        mDialog!!.setCancelable(cancelable)
        mDialog!!.setCanceledOnTouchOutside(cancelable)
        mDialog!!.show()
    }

    fun showLoader(context: Context) {
        if (mDialog != null && mDialog?.isShowing == true) {
            mProgressBar.let { mProgressBar.hideProgressBar() }
            mDialog?.dismiss()
            mDialog = null
        }
        mDialog = Dialog(context, R.style.LoaderTheme)
        mDialog!!.setContentView(R.layout.prograss_bar_dialog)
        mProgressBar = mDialog!!.findViewById(R.id.wp7_progressBar)
        mProgressBar.showProgressBar()
        mDialog!!.setCancelable(false)
        mDialog!!.setCanceledOnTouchOutside(false)
        mDialog!!.show()
    }

    fun hideLoader() {
        mDialog?.let {
            mProgressBar.let { mProgressBar.hideProgressBar() }
            mDialog?.dismiss()
            mDialog = null
        }
    }
}