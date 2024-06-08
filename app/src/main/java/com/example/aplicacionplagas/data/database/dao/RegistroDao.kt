package com.example.aplicacionplagas.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aplicacionplagas.data.database.entity.RegistroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(registro: RegistroEntity)

    @Query("SELECT * FROM registros")
    suspend fun obtenerRegistros(): List<RegistroEntity>

    @Query("SELECT * FROM registros WHERE id = :id")
    suspend fun obtenerRegistroConId(id : Long): RegistroEntity
    @Delete
    suspend fun eliminarRegistro(registroCaptura: RegistroEntity)

    @Query("DELETE FROM registros")
    suspend fun limpiarRegistros()

}