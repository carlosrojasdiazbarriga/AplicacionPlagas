package com.example.aplicacionplagas.data

import com.google.gson.annotations.SerializedName

data class ProbabilidadesPlaga(
    @SerializedName("Minador") val minador: Double,
    @SerializedName("MoscaBlanca") val moscaBlanca: Double,
    @SerializedName("Oidio") val oidio: Double,
    @SerializedName("Pulgon") val pulgon: Double,
    @SerializedName("roya") val roya: Double,
    @SerializedName("sana") val sana: Double
){
    fun obtenerCampoConProbabilidadMayor(): String {
        val map = mapOf(
            "Minador" to minador,
            "MoscaBlanca" to moscaBlanca,
            "Oidio" to oidio,
            "Pulgon" to pulgon,
            "roya" to roya,
            "sana" to sana
        )
        return map.maxByOrNull { it.value }?.key ?: "No hay datos"
    }
}
data class Result(
    @SerializedName("class_probabilities") val classProbabilities: ProbabilidadesPlaga
)

