package com.mukesh.reliv.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mesibo.api.Mesibo
import com.mesibo.api.Mesibo.MessageParams
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.ActivityChatBinding
import com.mukesh.reliv.model.SignUpDO
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity(), Mesibo.MessageListener, Mesibo.ConnectionListener {

    private lateinit var mBinding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val hmUser: HashMap<String, SignUpDO>? = Preferences.getUserHashMap()

        val arr: ArrayList<String> = ArrayList(hmUser?.keys!!)

        val signUpDO: String = arr[0]

        val api: Mesibo = Mesibo.getInstance()
        api.init(applicationContext)

        Mesibo.addListener(this)
        Mesibo.setSecureConnection(true)
        Mesibo.setAccessToken("a9073718b080167ed520f86dfb22ae47aed8a8bf349e1132e0bd")
        Mesibo.setDatabase("mydb", 0)
        Mesibo.start()
    }

    override fun Mesibo_onMessage(p0: Mesibo.MessageParams?, p1: ByteArray?): Boolean {
        return true
    }

    override fun Mesibo_onMessageStatus(p0: Mesibo.MessageParams?) {
    }

    override fun Mesibo_onActivity(p0: Mesibo.MessageParams?, p1: Int) {

    }

    override fun Mesibo_onLocation(p0: Mesibo.MessageParams?, p1: Mesibo.Location?) {
    }

    override fun Mesibo_onFile(p0: Mesibo.MessageParams?, p1: Mesibo.FileInfo?) {
    }

    override fun Mesibo_onConnectionStatus(p0: Int) {
    }

    /*fun onSendMessage(view: View?) {
        val p = MessageParams()
        p.peer = 7569858421
        p.flag = Mesibo.FLAG_READRECEIPT or Mesibo.FLAG_DELIVERYRECEIPT
        Mesibo.sendMessage(p, Mesibo.random(), mMessage.getText().toString().trim())
    }*/
}