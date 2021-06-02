package com.mukesh.reliv.view.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mesibo.api.Mesibo
import com.mesibo.api.Mesibo.MessageParams
import com.mesibo.calls.api.MesiboCall
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
        Mesibo.setAccessToken("a4c5c9258266565818b72989e0814db8693a32eaa60547ebb32e4b0")
        Mesibo.setDatabase("mydb", 0)
        Mesibo.start()

        mBinding.btnSend.setOnClickListener {
            MesiboUI.launch(this@ChatActivity, 0, false, true)
            onLaunchMessagingUi(it);
        }
    }

    private fun onLaunchMessagingUi(view: View?) {
        val mMesiboUIOptions: MesiboUI.Config = MesiboUI.getConfig()
        mMesiboUIOptions.userListTitle = "Vipin"
        MesiboUI.launchMessageView(this, "9405986565", 0)
    }

    override fun Mesibo_onMessage(p0: MessageParams?, p1: ByteArray?): Boolean {
        val msg = p1?.let { String(it) }
        MesiboUI.launchMessageView(this, p0?.profile?.address, 0)
//        MesiboCall.getInstance().init(applicationContext)
//        MesiboCall.getInstance().callUi(this, p0?.profile?.address, true)
        return true
    }

    override fun Mesibo_onMessageStatus(p0: MessageParams?) {
        Toast.makeText(
            this@ChatActivity,
            "Mesibo_onMessageStatus ${p0?.status}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun Mesibo_onActivity(p0: MessageParams?, p1: Int) {
        Toast.makeText(
            this@ChatActivity,
            "Mesibo_onActivity ${p0?.status}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun Mesibo_onLocation(p0: MessageParams?, p1: Mesibo.Location?) {
        Toast.makeText(
            this@ChatActivity,
            "Mesibo_onLocation ${p0?.status}",
            Toast.LENGTH_SHORT
        ).show()
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