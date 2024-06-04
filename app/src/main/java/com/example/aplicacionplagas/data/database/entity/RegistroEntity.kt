package com.example.aplicacionplagas.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aplicacionplagas.data.DatosPlaga
import com.example.aplicacionplagas.data.enums.Plagas

@Entity(tableName = "registros")
data class RegistroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Long = 0,
    @ColumnInfo(name = "plaga") val plaga : String = DatosPlaga().toString(),
    @ColumnInfo(name = "ubicacion") val ubicacion : String = "",
    @ColumnInfo(name = "fecha") val fecha : Long = System.currentTimeMillis(),
    @ColumnInfo(name = "fotoUri") val fotoUri : String = "",
)

fun obtenerNombreServicio(nombreCorrecto: String) : String {
    return Plagas.entries.find { it.nombreCorrecto.equals(nombreCorrecto, ignoreCase = true) }?.name ?: nombreCorrecto
}
