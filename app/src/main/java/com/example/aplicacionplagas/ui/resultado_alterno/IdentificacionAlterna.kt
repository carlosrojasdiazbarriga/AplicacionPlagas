package com.example.aplicacionplagas.ui.resultado_alterno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionplagas.databinding.LayoutIdentificacionAlternaBinding

class IdentificacionAlterna : AppCompatActivity(){
    private val binding by lazy {
        LayoutIdentificacionAlternaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.btnVolverIntentar.setOnClickListener{
            finish()
        }
        binding.imgVBack.setOnClickListener{
            finish()
        }
    }
}
