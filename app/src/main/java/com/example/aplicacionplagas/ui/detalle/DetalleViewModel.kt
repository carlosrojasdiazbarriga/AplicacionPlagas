package com.example.aplicacionplagas.ui.detalle

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.aplicacionplagas.data.database.AppDatabase
import com.example.aplicacionplagas.data.database.entity.RegistroEntity
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class DetalleViewModel : ViewModel() {
    val registroPlaga = MutableLiveData<RegistroEntity>()
    private lateinit var db: AppDatabase

    fun getPlaga(context: Context, idRegistro: Long) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()
        viewModelScope.launch {
            try {
                registroPlaga.value = db.getRegistroDao().obtenerRegistroConId(idRegistro)
            }catch (e: Exception){
                Log.e("Error", "Error al obtener registro $e")
            }
        }
    }
}
