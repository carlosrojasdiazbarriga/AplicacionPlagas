package com.example.aplicacionplagas.data

import com.google.gson.annotations.SerializedName


data class DatosPlaga (
    @SerializedName(value = "id") val id: Int = 0,
    @SerializedName(value = "nombre") val nombre: String = "",
    @SerializedName(value = "nombre_cientifico") val nombre_cientifico: String = "",
    @SerializedName(value = "otros_nombres") val otros_nombres: List<String> = emptyList(),
    @SerializedName(value = "descripcion") val descripcion: String = "",
    @SerializedName(value = "Metodos_de_eliminacion")val Metodos_de_eliminacion : List<String> = emptyList(),
    @SerializedName(value = "Metodos_de_prevencion") val Metodos_de_prevencion : List<String> = emptyList(),
    @SerializedName(value = "plantas_objetivo") val plantas_objetivo : String = "",
    @SerializedName(value = "plantas_defensivas") val plantas_defensivas : String = "",
)
