package com.mukesh.reliv.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.ActivityHelpScreenBinding
import com.mukesh.reliv.view.adapters.HelpScreenPagerAdapter
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import java.util.*

class HelpScreenActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityHelpScreenBinding
    private var currentPage: Int = 0
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHelpScreenBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white)
        }

        val pagerAdapter = HelpScreenPagerAdapter(this)
        mBinding.vpHelpBanner.adapter = pagerAdapter

        timer = Timer()

        mBinding.indicatorView.apply {
            setSliderColor(
                resources.getColor(R.color.gray_light_color),
                resources.getColor(R.color.primary_dark_color)
            )
            setSliderWidth(resources.getDimension(R.dimen._10sdp))
            setSliderHeight(resources.getDimension(R.dimen._5sdp))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setPageSize(mBinding.vpHelpBanner.adapter!!.itemCount)
            notifyDataChanged()
        }

        mBinding.vpHelpBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                mBinding.indicatorView.onPageScrolled(
                    position,
                    positionOffset,
                    positionOffsetPixels
                )
                currentPage = position
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.indicatorView.onPageSelected(position)
            }
        })

        mBinding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this@HelpScreenActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val handler = Handler()
        val update = Runnable {
            if (currentPage == 3) {
                currentPage = 0
            }
            //The second parameter ensures smooth scrolling
            mBinding.vpHelpBanner.setCurrentItem(currentPage++, true)
        }

        timer.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(update)
            }
        }, 2000, 2000)
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
}