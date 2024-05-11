package com.example.aplicacionplagas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionplagas.databinding.ActivityMainBinding
import com.example.aplicacionplagas.ui.capturar.Capturar
import com.example.aplicacionplagas.ui.historial.Historial


class MainActivity : AppCompatActivity() {
    val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializar()
    }

    private fun inicializar() {
        binding.apply {
            BCapturar.setOnClickListener{
                val intent = Intent(this@MainActivity, Capturar::class.java)
                startActivity(intent)
            }
            BHistorial.setOnClickListener{
                val intent = Intent(this@MainActivity, Historial::class.java)
                startActivity(intent)
            }
            BPrevenir.setOnClickListener{

            }
        }
    }
}