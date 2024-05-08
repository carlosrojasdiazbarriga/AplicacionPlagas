package com.example.aplicacionplagas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aplicacionplagas.data.entity.RegistroCaptura

@Dao
interface RegistroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(registro: RegistroCaptura)

    @Query("SELECT * FROM registros")
    fun obtenerRegistros(): List<RegistroCaptura>

    @Query("SELECT * FROM registros WHERE id = :id")
    fun obtenerRegistro(id: Long):RegistroCaptura

}