package com.example.aplicacionplagas

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.aplicacionplagas.data.AppDatabase
import com.example.aplicacionplagas.data.entity.DatosPlaga
import com.example.aplicacionplagas.data.entity.Plagas
import com.example.aplicacionplagas.data.entity.RegistroCaptura
import com.example.aplicacionplagas.databinding.LayoutResultadoBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Resultado : AppCompatActivity() {
    private lateinit var db: AppDatabase

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val binding: LayoutResultadoBinding by lazy {
        LayoutResultadoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        traerDatosPlaga()
    }

    private fun traerDatosPlaga() {
        val resultado = intent.getStringExtra("resultado")
        val uri = intent.getStringExtra("uri")?.toUri()
        val plaga: DatosPlaga = Gson().fromJson(resultado, DatosPlaga::class.java)

        if (plaga.nombre != "sana" && uri != null){
            binding.TPlaga.text = Plagas.obtenerNombreCorrecto(plaga.nombre)
            binding.TDescripcion.text = plaga.descripcion
            binding.TCombate.text = plaga.Metodos_de_eliminacion.joinToString("\n")
            binding.IPlaga.setImageURI(uri)
            guardarRegistro(plaga, uri)
        }
    }
    private fun guardarRegistro(plaga: DatosPlaga, uri: Uri) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        obtenerUbicacionActual { ubicacion ->
            val registro = RegistroCaptura(
                plaga =  plaga.toString(),
                fotoUri = uri.toString(),
                ubicacion = ubicacion
            )
            lifecycleScope.launch(Dispatchers.IO) {
                db.registroDao().insertar(registro)
            }
            Log.d("Registro","Ubicación actual: $ubicacion")
        }
    }
    private fun obtenerUbicacionActual(callback: (String) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitud = it.latitude
                    val longitud = it.longitude
                    obtenerCiudadPais(latitud, longitud, callback)
                } ?: run {
                    callback.invoke("Ubicación no disponible")
                }
            }
            .addOnFailureListener { e ->
                callback.invoke("Error al obtener la ubicación: ${e.message}")
            }
    }
    private fun obtenerCiudadPais(latitud: Double, longitud: Double, callback: (String) -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {
            val geocoder = Geocoder(this@Resultado)
            val direcciones = geocoder.getFromLocation(latitud, longitud, 1)
            if (direcciones != null) {
                if (direcciones.isNotEmpty()) {
                    val direccion = direcciones[0]
                    val ciudad = direccion.locality
                    val pais = direccion.countryName
                    val ubicacion = "$ciudad, $pais"
                    callback.invoke(ubicacion)
                } else {
                    callback.invoke("Ubicación no disponible")
                }
            }
        }
    }
}