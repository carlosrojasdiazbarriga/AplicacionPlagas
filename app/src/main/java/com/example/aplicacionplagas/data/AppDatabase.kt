package com.example.aplicacionplagas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionplagas.data.dao.RegistroDao
import com.example.aplicacionplagas.data.entity.RegistroCaptura

@Database (entities = [RegistroCaptura::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registroDao(): RegistroDao
}