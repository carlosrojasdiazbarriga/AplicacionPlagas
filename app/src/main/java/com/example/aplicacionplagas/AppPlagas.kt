package com.example.aplicacionplagas

import android.app.Application
import androidx.room.Room
import com.example.aplicacionplagas.data.database.AppDatabase
import java.util.concurrent.Executors

class AppPlagas : Application() {
    companion object {
        lateinit var instance: AppPlagas
            private set
    }

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database-name")
            .setQueryExecutor(Executors.newSingleThreadExecutor())
            .build()
    }
}