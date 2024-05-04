package com.example.aplicacionplagas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacionplagas.databinding.ActivityMainBinding


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
                val intent = Intent(this@MainActivity,Capturar::class.java)
                startActivity(intent)
            }
            BHistorial.setOnClickListener{

            }
            BPrevenir.setOnClickListener{

            }
        }
    }
}