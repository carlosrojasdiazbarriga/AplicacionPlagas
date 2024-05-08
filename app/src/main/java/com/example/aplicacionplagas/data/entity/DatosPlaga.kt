package com.example.aplicacionplagas.data.entity


data class DatosPlaga (
    val id: Int = 0,
    val nombre: String = "",
    val nombre_cientifico: String = "",
    val otros_nombres: List<String> = emptyList(),
    val descripcion: String = "",
    val Metodos_de_eliminacion : List<String> = emptyList(),
    val Metodos_de_prevencion : List<String> = emptyList(),
    val plantas_objetivo : String = "",
    val plantas_defensivas : String = "",
)