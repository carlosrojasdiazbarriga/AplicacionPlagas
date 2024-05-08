package com.example.aplicacionplagas.data.entity

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
    }
}