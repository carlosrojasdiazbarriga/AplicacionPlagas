package com.example.aplicacionplagas.data

import android.os.Bundle
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

        traerDatosPlaga()
        guardarRegistro()
    }

    private fun guardarRegistro() {
        //TODO Guardar la plaga, foto, ubicación y fecha de registro
    }

    private fun traerDatosPlaga() {
        val resultado = intent.getStringExtra("resultado")
        val result: DatosPlaga = Gson().fromJson(resultado, DatosPlaga::class.java)

        if (result.nombre != "sana"){
            binding.TPlaga.text = Plagas.obtenerNombreCorrecto(result.nombre)
            binding.TDescripcion.text = result.descripcion
            binding.TCombate.text = result.Metodos_de_eliminacion.joinToString("\n")
        }
    }
}