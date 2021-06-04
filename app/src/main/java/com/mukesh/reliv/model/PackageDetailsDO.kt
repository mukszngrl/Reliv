package com.mukesh.reliv.model

data class PackageDetailsDO(
    val id: Int,
    val packageName: String,
    val packageAmt: Double,
    val duration: String,
    val description: String,
    val photoURL: Int
)
