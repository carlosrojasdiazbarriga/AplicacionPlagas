package com.example.aplicacionplagas.ui.prevencion

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionplagas.data.enums.Plagas
import com.example.aplicacionplagas.databinding.LayoutPrevencionBinding

class Prevenir : AppCompatActivity(){
    private val binding : LayoutPrevencionBinding by lazy{
        LayoutPrevencionBinding.inflate(layoutInflater)
    }
    private lateinit var adapter : PrevencionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = PrevencionAdapter(Plagas.plagas, this)
        binding.rvPrevencion.adapter = adapter
        binding.rvPrevencion.layoutManager = GridLayoutManager(this,2)
    }
}
