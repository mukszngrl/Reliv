package com.mukesh.reliv.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mesibo.api.Mesibo
import com.mesibo.api.Mesibo.MessageParams
import com.mesibo.messaging.MesiboUI
import com.mukesh.reliv.databinding.ActivityChatBinding
import java.util.*

class ChatActivity : AppCompatActivity(), Mesibo.MessageListener, Mesibo.ConnectionListener {

    private lateinit var mBinding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val api: Mesibo = Mesibo.getInstance()
        api.init(applicationContext)

        Mesibo.addListener(this)
        Mesibo.setSecureConnection(true)
        Mesibo.setAccessToken("ccbf0f8b457746734ca06c9af036e3ddc50a239d17123ce6632ec8b")
        Mesibo.setDatabase("mydb", 0)
        Mesibo.start()

        mBinding.btnSend.setOnClickListener {
//            MesiboUI.launch(this@ChatActivity, 0, false, true)
            onLaunchMessagingUi()
        }
    }

    private fun onLaunchMessagingUi() {
        val mProfile = Mesibo.UserProfile()
        mProfile.address = "7569858421"
        mProfile.name = "Vipin"
        Mesibo.setUserProfile(mProfile, false)
        MesiboUI.launchMessageView(this, mProfile.address, 0)

        /*val intent = Intent(this, MessageViewActivity::class.java)
        intent.putExtra("address", "7569858421")
        intent.putExtra("name", "Vipin")
        startActivity(intent)*/
    }

    override fun Mesibo_onMessage(p0: MessageParams?, p1: ByteArray?): Boolean {
        val msg = p1?.let { String(it) }
        return true
    }

    override fun Mesibo_onMessageStatus(p0: MessageParams?) {
        /*Toast.makeText(
            this@ChatActivity,
            "Mesibo_onMessageStatus ${p0?.status}",
            Toast.LENGTH_SHORT
        ).show()*/
    }

    override fun Mesibo_onActivity(p0: MessageParams?, p1: Int) {
        /*Toast.makeText(
            this@ChatActivity,
            "Mesibo_onActivity ${p0?.status}",
            Toast.LENGTH_SHORT
        ).show()*/
    }

    override fun Mesibo_onLocation(p0: MessageParams?, p1: Mesibo.Location?) {
        /*Toast.makeText(
            this@ChatActivity,
            "Mesibo_onLocation ${p0?.status}",lk,
            Toast.LENGTH_SHORT
        ).show()*/
    }

    override fun Mesibo_onFile(p0: MessageParams?, p1: Mesibo.FileInfo?) {
    }

    override fun Mesibo_onConnectionStatus(p0: Int) {
        Toast.makeText(
            this@ChatActivity,
            "Mesibo_onConnectionStatus $p0",
            Toast.LENGTH_SHORT
        ).show()
    }

    /*private fun onSendMessage() {
        val p = MessageParams()
        p.peer = "7569858421"
        p.flag = Mesibo.FLAG_READRECEIPT or Mesibo.FLAG_DELIVERYRECEIPT
        Mesibo.sendMessage(p, Mesibo.random(), mBinding.etMessage.text.toString().trim())
    }*/
}