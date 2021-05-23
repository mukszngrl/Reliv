package com.mukesh.reliv.view.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.reliv.databinding.ActivityMainBinding
import com.mukesh.reliv.view.adapters.WeatherAdapter
import com.mukesh.reliv.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var weatherAdapter: WeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        context = this@MainActivity

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mBinding.btnClick.setOnClickListener {
            mBinding.wp7progressBar.showProgressBar()

            /*mainActivityViewModel.getUser()!!.observe(this, { serviceSetterGetter ->
                mBinding.wp7progressBar.hideProgressBar()
                val msg = serviceSetterGetter.message
                mBinding.lblResponse.text = msg
            })*/

            mainActivityViewModel.getWeather()!!.observe(this, { weatherGetterSetter ->
                mBinding.wp7progressBar.hideProgressBar()

                if (weatherAdapter == null) {
                    weatherAdapter = WeatherAdapter(weatherGetterSetter.data, context)
                    mBinding.rvWeather.layoutManager = LinearLayoutManager(context)
                    mBinding.rvWeather.adapter = weatherAdapter;
                } else {
                    weatherAdapter!!.refresh(weatherGetterSetter.data)
                }
            })
        }
    }
}