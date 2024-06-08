package com.example.aplicacionplagas.ui.prevencion

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionplagas.databinding.LayoutPrevencionBinding

class Prevenir : AppCompatActivity(){
    private val binding : LayoutPrevencionBinding by lazy{
        LayoutPrevencionBinding.inflate(layoutInflater)
    }
    private val viewModel : PrevenirViewModel by viewModels()
    private lateinit var adapter : PrevencionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val plagas = listOf(
            "Minador" to "minador",
            "Mosca Blanca" to "mosca_blanca",
            "Oidio" to "oidio",
            "Pulgón" to "pulgon",
            "Roya" to "roya"
        )
        adapter = PrevencionAdapter(plagas, this)
        binding.rvPrevencion.adapter = adapter
        binding.rvPrevencion.layoutManager = GridLayoutManager(this,2)
    }
}
