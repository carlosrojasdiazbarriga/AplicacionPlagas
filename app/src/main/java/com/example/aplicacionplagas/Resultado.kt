package com.example.aplicacionplagas

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionplagas.databinding.LayoutResultadoBinding
import com.google.gson.Gson

class Resultado : AppCompatActivity() {
    private val binding : LayoutResultadoBinding by lazy {
        LayoutResultadoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        desplegarResultado()

    }

    private fun desplegarResultado() {
        val bundle = intent.extras
        val resultado = bundle?.getString("resultado").toString()

        val result: Result = Gson().fromJson(resultado, Result::class.java)

        val probabilidad = result.classProbabilities

        binding.TPlaga.text = probabilidad.obtenerCampoConProbabilidadMayor()
    }
}