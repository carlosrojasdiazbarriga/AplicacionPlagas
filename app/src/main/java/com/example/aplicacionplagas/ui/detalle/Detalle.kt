package com.example.aplicacionplagas.ui.detalle

import android.app.ProgressDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.aplicacionplagas.data.DatosPlaga
import com.example.aplicacionplagas.data.database.entity.RegistroEntity
import com.example.aplicacionplagas.data.enums.Plagas
import com.example.aplicacionplagas.databinding.LayoutResultadoBinding
import com.google.gson.Gson

class Detalle : AppCompatActivity(){
    private val viewModel: DetalleViewModel by viewModels()

    private val binding: LayoutResultadoBinding by lazy {
        LayoutResultadoBinding.inflate(layoutInflater)
    }
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListeners()
        displayLoader()
        traerRegistroPlaga()
    }

    private fun initListeners() {
        binding.button.setOnClickListener {
            finish()
        }
    }

    private fun traerRegistroPlaga() {
        val idRegistro = intent.getLongExtra("id",0)
        viewModel.getPlaga(applicationContext,idRegistro)
        viewModel.registroPlaga.observe(this) {
            if (it != null) {
                initViews(it)
                hideLoader()
            }
        }
    }

    private fun initViews(registro: RegistroEntity) {
        val datosPlaga = Gson().fromJson(registro.plaga, DatosPlaga::class.java)

        binding.apply {
            TPlaga.text = Plagas.obtenerNombreCorrecto(datosPlaga.nombre)
            TDescripcion.text = datosPlaga.descripcion
            TCombate.text = datosPlaga.Metodos_de_eliminacion.joinToString("\n")
            IPlaga.setImageURI(registro.fotoUri.toUri())
        }
    }


    private fun displayLoader() {
        progressDialog = ProgressDialog(this)
        progressDialog.apply {
            setMessage("Analizando...")
            setCancelable(false)
            show()
        }
    }

    private fun hideLoader() {
        progressDialog.dismiss()
    }
}
