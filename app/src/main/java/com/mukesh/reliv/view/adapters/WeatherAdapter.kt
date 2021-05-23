package com.mukesh.reliv.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.reliv.R
import com.mukesh.reliv.databinding.WeatherCellBinding
import com.mukesh.reliv.model.WeatherDO
import com.mukesh.reliv.view.activities.PaymentActivity
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(arrWeathers: List<WeatherDO>?, context: Context) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {
    private var arrWeathers: List<WeatherDO>?
    private val context: Context

    init {
        this.arrWeathers = arrWeathers
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding: WeatherCellBinding =
            WeatherCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherDO: WeatherDO = arrWeathers!![position]
        holder.tvTemp.text =
            weatherDO.temp.toString() + "" + context.resources.getString(R.string.degree_sign) + "C"
        holder.tvDate.text = getDate(weatherDO.time, "MMM dd yyyy")
        holder.tvRain.text = weatherDO.rain.toString() + "%"
        holder.tvWind.text = weatherDO.wind.toString() + " km/h"

        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    PaymentActivity::class.java
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return arrWeathers?.size ?: 0
    }

    fun refresh(arrWeathers: List<WeatherDO>?) {
        this.arrWeathers = arrWeathers
        notifyDataSetChanged()
    }

    class MyViewHolder(itemBinding: WeatherCellBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val tvTemp: TextView = itemBinding.tvTemp
        val tvRain: TextView = itemBinding.tvRain
        val tvWind: TextView = itemBinding.tvWind
        val tvDate: TextView = itemBinding.tvDate

    }

    private fun getDate(milliSeconds: Long, dateFormat: String?): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}