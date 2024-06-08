package com.example.aplicacionplagas.data.enums

enum class Plagas(val nombreCorrecto: String) {
    Minador("Minador"),
    MoscaBlanca("Mosca Blanca"),
    Oidio("Oidio"),
    Pulgon("Pulgón"),
    Roya("Roya"),
    Sana("Sana");

    companion object {
        fun obtenerNombreCorrecto(nombreIncorrecto: String): String {
            return entries.find { it.name.equals(nombreIncorrecto, ignoreCase = true) }?.nombreCorrecto ?: nombreIncorrecto
        }

        fun obtenerNombreServicio(nombreCorrecto: String): String {
            return entries.find { it.nombreCorrecto.equals(nombreCorrecto, ignoreCase = true) }?.name ?: nombreCorrecto
        }

        val plagas = listOf(
            "Minador" to "minador",
            "Mosca Blanca" to "mosca_blanca",
            "Oidio" to "oidio",
            "Pulgón" to "pulgon",
            "Roya" to "roya"
        )
    }
}