package com.example.aplicacionplagas.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.aplicacionplagas.R

object ImageHelper {
    fun getImageBitmapByResId(context: Context, imageResid: String) : Bitmap? {
        val imageResId = context.resources.getIdentifier(imageResid, "raw", context.packageName)
        if (imageResId != 0) {
            val imageStream = context.resources.openRawResource(imageResId)
            val bitmap = BitmapFactory.decodeStream(imageStream)
            return bitmap
        }
        return null
    }
}