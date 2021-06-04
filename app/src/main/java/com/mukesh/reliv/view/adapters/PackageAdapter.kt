package com.mukesh.reliv.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.reliv.databinding.PackageSelectionCellBinding
import com.mukesh.reliv.model.PackageDetailsDO
import com.mukesh.reliv.view.activities.PackageSelectionActivity
import java.text.NumberFormat
import java.util.*

class PackageAdapter(private val activity: Activity) :
    RecyclerView.Adapter<PackageAdapter.MyViewHolder>() {

    private var arrPackage: List<PackageDetailsDO> = listOf()

    class MyViewHolder(view: PackageSelectionCellBinding) : RecyclerView.ViewHolder(view.root) {
        val ivPackageImage = view.ivPackageImage
        val tvPackageName = view.tvPackageName
        val tvPackageDesc = view.tvPackageDesc
        val tvPackageAmt = view.tvPackageAmt
        val tvDuration = view.tvDuration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: PackageSelectionCellBinding =
            PackageSelectionCellBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val packageDetails = arrPackage[position]

        packageDetails.let {
            holder.ivPackageImage.setImageResource(it.photoURL)
            holder.tvPackageName.text = it.packageName
            holder.tvPackageDesc.text = it.description
            holder.tvPackageAmt.text =
                NumberFormat.getInstance(Locale.ENGLISH).format(it.packageAmt);
            holder.tvDuration.text = it.duration

            holder.itemView.setOnClickListener {
                if (activity is PackageSelectionActivity)
                    activity.proceedToPayment(
                        packageDetails.packageAmt.toString(),
                        packageDetails.packageName
                    )
            }
        }
    }

    override fun getItemCount(): Int {
        return arrPackage.size
    }

    fun refreshList(arrPackage: List<PackageDetailsDO>) {
        this.arrPackage = arrPackage;
        notifyDataSetChanged()
    }
}