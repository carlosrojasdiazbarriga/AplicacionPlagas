package com.example.aplicacionplagas.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateHelper {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    fun getDate(fecha: Long): String {
        return dateFormatter.format(fecha)
    }
}
