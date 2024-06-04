package com.example.aplicacionplagas.ui.detalle

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.room.Room
import com.example.aplicacionplagas.data.DatosPlaga
import com.example.aplicacionplagas.data.ProbabilidadesPlaga
import com.example.aplicacionplagas.data.Result
import com.example.aplicacionplagas.data.database.AppDatabase
import com.example.aplicacionplagas.data.database.entity.RegistroEntity
import com.example.aplicacionplagas.data.database.entity.obtenerNombreServicio
import com.example.aplicacionplagas.data.enums.Plagas
import com.example.aplicacionplagas.databinding.LayoutResultadoBinding
import com.example.aplicacionplagas.ui.resultado.Resultado
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.URI
import java.util.concurrent.TimeUnit

class Detalle : AppCompatActivity(){
    private val viewModel: DetalleViewModel by viewModels()

    private val binding: LayoutResultadoBinding by lazy {
        LayoutResultadoBinding.inflate(layoutInflater)
    }
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        displayLoader()
        traerRegistroPlaga()
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
            for (metodo in datosPlaga.Metodos_de_eliminacion) {
                TCombate.append("\n$metodo")
            }
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
