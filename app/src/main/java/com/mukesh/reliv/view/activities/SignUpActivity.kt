package com.mukesh.reliv.view.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignUpBinding
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

//        navController = Navigation.findNavController(this, R.id.fragment)
        Navigation.findNavController(this, R.id.fragment).setGraph(R.navigation.nav_graph, intent.extras)

        /*NavigationUI.setupActionBarWithNavController(this, navController)
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFF"))
        actionBar?.setBackgroundDrawable(colorDrawable)
        actionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_back_black))
        actionBar?.elevation = 0F*/
    }
}