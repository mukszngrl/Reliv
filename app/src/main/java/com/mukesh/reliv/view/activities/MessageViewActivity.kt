package com.mukesh.reliv.view.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mesibo.api.Mesibo
import com.mesibo.calls.api.MesiboCall
import com.mesibo.messaging.MesiboMessagingFragment
import com.mesibo.messaging.MesiboUI
import com.mesibo.messaging.MesiboUserListFragment
import com.mukesh.reliv.R


class MessageViewActivity : AppCompatActivity(), MesiboMessagingFragment.FragmentListener {
    private lateinit var peer: String
    private lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_view)

        peer = intent.getStringExtra("address").toString()
        name = intent.getStringExtra("name").toString()

        val userListFragment = MesiboMessagingFragment()
        userListFragment.setListener()

        val bl = Bundle()
        bl.putString(MesiboUI.PEER, peer)
        userListFragment.arguments = bl

        supportFragmentManager.beginTransaction()
            .replace(R.id.userlist_fragment, userListFragment, "null")
            .commit()

        setTitleBar()

        MesiboCall.getInstance().init(applicationContext)
    }

    private fun setTitleBar() {
        // calling the action bar
        val actionBar = supportActionBar
        if (actionBar != null) {
            // setting the title
            actionBar.title = name
            actionBar.setLogo(R.drawable.reliv_logo)
            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_chat_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_voice_call -> {
                MesiboCall.getInstance().callUi(this, peer, false)
                return true
            }
            R.id.action_video_call -> {
                MesiboCall.getInstance().callUi(this, peer, true)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun Mesibo_onUpdateUserPicture(p0: Mesibo.UserProfile?, p1: Bitmap?, p2: String?) {
    }

    override fun Mesibo_onUpdateUserOnlineStatus(p0: Mesibo.UserProfile?, p1: String?) {
    }

    override fun Mesibo_onShowInContextUserInterface() {
    }

    override fun Mesibo_onHideInContextUserInterface() {
    }

    override fun Mesibo_onContextUserInterfaceCount(p0: Int) {
    }

    override fun Mesibo_onError(p0: Int, p1: String?, p2: String?) {
    }
}