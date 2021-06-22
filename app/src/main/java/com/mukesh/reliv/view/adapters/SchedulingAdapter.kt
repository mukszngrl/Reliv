package com.mukesh.reliv.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.reliv.common.CalendarUtils
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.SchedulingCellBinding
import com.mukesh.reliv.model.DoctorScheduleTimingsDO
import com.mukesh.reliv.model.PatientScheduleTimingsDO
import com.mukesh.reliv.view.activities.DashboardActivity

class SchedulingAdapter(context: Context) :
    RecyclerView.Adapter<SchedulingAdapter.MyViewHolder>() {
    private var arrPatientSchedule: ArrayList<PatientScheduleTimingsDO>? = null
    private var arrDoctorSchedule: ArrayList<DoctorScheduleTimingsDO>? = null
    private val context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding: SchedulingCellBinding =
            SchedulingCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (Preferences.getStringFromPreference(Preferences.USER_TYPE, "Patient")
                .equals("Patient")
        ) {
            val patientSchedule = arrPatientSchedule?.get(position)
            if (patientSchedule != null) {
                holder.tvDoctorName.text = patientSchedule.Dd_Name
                holder.tvDoctorSpeciality.text = patientSchedule.Dd_Designation
                holder.tvSchedule.text = CalendarUtils.changeDateFormat(
                    patientSchedule.SchedulerTime,
                    CalendarUtils.YYYY_MM_DD_T_HH_MM_SS_PATTERN,
                    CalendarUtils.DD_MMM_YYYY_HH_MM_A_PATTERN
                )

                holder.itemView.setOnClickListener {
                    (context as DashboardActivity).onLaunchMessagingUi(patientSchedule, null)
                }
            }
        } else {
            val doctorSchedule = arrDoctorSchedule?.get(position)
            if (doctorSchedule != null) {
                holder.tvDoctorName.text = doctorSchedule.Dd_Name
                holder.tvDoctorSpeciality.text = doctorSchedule.Dd_Designation
                holder.tvSchedule.text = doctorSchedule.SchedulerTime

                holder.itemView.setOnClickListener {
                    (context as DashboardActivity).onLaunchMessagingUi(null, doctorSchedule)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (Preferences.getStringFromPreference(Preferences.USER_TYPE, "Patient")
                .equals("Patient")
        )
            arrPatientSchedule?.size ?: 0
        else
            arrDoctorSchedule?.size ?: 0
    }

    fun refresh(
        arrDoctorSchedule: ArrayList<DoctorScheduleTimingsDO>?,
        arrPatientSchedule: ArrayList<PatientScheduleTimingsDO>?
    ) {
        this.arrDoctorSchedule = arrDoctorSchedule
        this.arrPatientSchedule = arrPatientSchedule
        notifyDataSetChanged()
    }

    class MyViewHolder(itemBinding: SchedulingCellBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val tvDoctorName: TextView = itemBinding.tvDoctorName
        val tvDoctorSpeciality: TextView = itemBinding.tvDoctorSpeciality
        val tvSchedule: TextView = itemBinding.tvSchedule
    }
}