package com.mukesh.reliv.common

import android.content.Context
import android.graphics.Typeface

object AppConstants {

    lateinit var HELVETICA: Typeface
    lateinit var HELVETICA_BOLD: Typeface
    lateinit var HELVETICA_BOLD_OBLIQUE: Typeface
    lateinit var HELVETICA_COMPRESSED: Typeface
    lateinit var HELVETICA_LIGHT: Typeface
    lateinit var HELVETICA_OBLIQUE: Typeface
    lateinit var HELVETICA_ROUNDED_BOLD: Typeface


    fun initializeTypeFace(context: Context) {
        try {
            HELVETICA = Typeface.createFromAsset(context.assets, "helvetica.ttf")
            HELVETICA_BOLD = Typeface.createFromAsset(context.assets, "helvetica_bold.ttf")
            HELVETICA_BOLD_OBLIQUE = Typeface.createFromAsset(context.assets, "helvetica_bold_oblique.ttf");
            HELVETICA_COMPRESSED = Typeface.createFromAsset(context.assets, "helvetica_compressed.otf")
            HELVETICA_LIGHT = Typeface.createFromAsset(context.assets, "helvetica_light.ttf")
            HELVETICA_OBLIQUE = Typeface.createFromAsset(context.assets, "helvetica_oblique.ttf")
            HELVETICA_ROUNDED_BOLD = Typeface.createFromAsset(context.assets, "helvetica_rounded_bold.otf")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}