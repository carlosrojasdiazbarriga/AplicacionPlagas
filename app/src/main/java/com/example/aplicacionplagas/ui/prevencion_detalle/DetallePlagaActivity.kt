package com.example.aplicacionplagas.ui.prevencion_detalle

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.example.aplicacionplagas.R
import com.example.aplicacionplagas.data.enums.EstadoConsulta
import com.example.aplicacionplagas.data.enums.Plagas
import com.example.aplicacionplagas.databinding.LayoutResultadoBinding
import com.example.aplicacionplagas.util.ImageHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetallePrevencionActivity : AppCompatActivity() {
    private val binding by lazy { LayoutResultadoBinding.inflate(layoutInflater) }
    private val viewModel : DetallePlagaViewModel by viewModels()

    private var nombrePlaga : String? = ""

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        nombrePlaga = intent.getStringExtra("nombre")

        desplegarDatosGuardados()
        observarEstadoConsulta()
        getInformacionPlaga()
    }

    private fun desplegarDatosGuardados() {
        val imageResId = Plagas.plagas.find { it.first == nombrePlaga }?.second

        binding.TPlaga.text = nombrePlaga

        if (imageResId != null) {
            val bitmap = ImageHelper.getImageBitmapByResId(this, imageResId)
            if (bitmap != null) {
                binding.IPlaga.setImageBitmap(bitmap)
            } else {
                binding.IPlaga.setImageResource(R.drawable.ic_launcher_background)
            }
        } else {
            binding.IPlaga.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    private fun observarEstadoConsulta() {
        viewModel.estadoConsulta.observe(this){
            when(it){
                EstadoConsulta.LOADING -> displayLoader()
                EstadoConsulta.TIME_OUT -> terminarConsulta()
                EstadoConsulta.OK -> displayData()
                EstadoConsulta.ERROR -> terminarConsulta()
            }
        }
    }

    private fun displayData() {
        progressDialog.dismiss()
        viewModel.dataPlaga.observe(this){
            binding.apply {
                TDescripcion.text = it.descripcion
                TCombate.text = it.Metodos_de_prevencion.joinToString("\n")
            }
        }
    }

    private fun terminarConsulta() {
        Toast.makeText(this, "Por favor, vuelva a consultar más tarde", Toast.LENGTH_SHORT).show()
        viewModel.viewModelScope.launch {
            delay(2000)
            finish()
        }
    }

    private fun displayLoader() {
        progressDialog = ProgressDialog(this)
        progressDialog.apply {
            setMessage("Obteniendo información...")
            setCancelable(false)
            show()
        }
    }

    private fun getInformacionPlaga() {
        viewModel.traerInformacionPlaga(Plagas.obtenerNombreServicio(nombrePlaga?:""))
    }
}
