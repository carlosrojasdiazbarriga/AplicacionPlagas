package com.example.aplicacionplagas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aplicacionplagas.data.database.dao.RegistroDao
import com.example.aplicacionplagas.data.database.entity.RegistroEntity

@Database (entities = [RegistroEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRegistroDao(): RegistroDao
}