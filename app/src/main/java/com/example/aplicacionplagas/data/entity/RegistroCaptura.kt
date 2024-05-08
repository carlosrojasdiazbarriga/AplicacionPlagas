package com.example.aplicacionplagas.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registros")
data class RegistroCaptura(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Long = 0,
    @ColumnInfo(name = "plaga") val plaga : String = DatosPlaga().toString(),
    @ColumnInfo(name = "ubicacion") val ubicacion : String = "",
    @ColumnInfo(name = "fecha") val fecha : Long = System.currentTimeMillis(),
    @ColumnInfo(name = "fotoUri") val fotoUri : String = "",
)