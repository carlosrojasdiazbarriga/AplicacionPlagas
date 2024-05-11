package com.example.aplicacionplagas.ui.historial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionplagas.AppPlagas
import com.example.aplicacionplagas.data.database.dao.RegistroDao
import com.example.aplicacionplagas.data.database.entity.RegistroEntity
import kotlinx.coroutines.launch

class HistorialViewModel(private val dao : RegistroDao) : ViewModel() {
    val historial = MutableLiveData<List<RegistroEntity>>()

    fun getRegistros(){
        viewModelScope.launch {
            try {
                val registros = dao.obtenerRegistros()
                historial.value = registros
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
