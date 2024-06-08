package com.example.aplicacionplagas.ui.prevencion_detalle

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionplagas.data.DatosPlaga
import com.example.aplicacionplagas.data.Result
import com.example.aplicacionplagas.data.enums.EstadoConsulta
import com.example.aplicacionplagas.ui.resultado.Resultado
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class DetallePlagaViewModel : ViewModel() {

    private val _dataPlaga = MutableLiveData<DatosPlaga>()
    val dataPlaga: LiveData<DatosPlaga> = _dataPlaga

    private val _estadoConsulta = MutableLiveData<EstadoConsulta>()
    val estadoConsulta : LiveData<EstadoConsulta> = _estadoConsulta

    fun traerInformacionPlaga(plaga : String) {
        _estadoConsulta.postValue(EstadoConsulta.LOADING)

        val TIMEOUT_SECONDS = 10L

        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url("https://fastapi-jzsq5fw66a-wl.a.run.app/plagas/$plaga")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                if (e is SocketTimeoutException) {
                    _estadoConsulta.postValue(EstadoConsulta.TIME_OUT)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    if (!responseData.isNullOrEmpty()) {
                        _dataPlaga.postValue(Gson().fromJson(responseData, DatosPlaga::class.java))
                        _estadoConsulta.postValue(EstadoConsulta.OK)
                    } else {
                        Log.e("error", "El cuerpo de la respuesta es nulo")
                    }
                } else {
                    Log.e("error", response.message)
                    _estadoConsulta.postValue(EstadoConsulta.ERROR)
                }
            }
        })
    }

}
