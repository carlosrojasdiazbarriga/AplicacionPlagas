package com.example.aplicacionplagas.ui.historial

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.aplicacionplagas.data.database.AppDatabase
import com.example.aplicacionplagas.data.database.entity.RegistroEntity
import com.example.aplicacionplagas.databinding.LayoutHistorialBinding
import com.example.aplicacionplagas.ui.detalle.Detalle

class Historial : AppCompatActivity(){
    private lateinit var db : AppDatabase
    private val binding by lazy {
        LayoutHistorialBinding.inflate(layoutInflater)
    }
    private lateinit var historialAdapter : HistorialAdapter
    private val viewModel: HistorialViewModel by viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HistorialViewModel(db.getRegistroDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()

        observarDatos()
        setRecyclerView()
    }

    private fun observarDatos() {
        viewModel.getRegistros()
        viewModel.historial.observe(this) {
            historialAdapter.submitList(it)
        }
    }

    private fun setRecyclerView() {
        historialAdapter = HistorialAdapter()
        historialAdapter.setOnClickListener(object : OnItemClickedRegistro {
            override fun onItemClicked(registro: RegistroEntity) {
                val intent = Intent(this@Historial, Detalle::class.java)
                intent.putExtra("id", registro.id)
                intent.putExtra("nombre", registro.plaga)
                startActivity(intent)
            }
        })
        binding.rvHistorial.adapter = historialAdapter
        binding.rvHistorial.layoutManager = LinearLayoutManager(this)

    }
}