package com.example

import android.app.Application
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.AppRepository

class HalChalApplication : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var repository: AppRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "halchal_database"
        ).build()
        repository = AppRepository(
            database.chatDao(),
            database.messageDao(),
            database.userDao(),
            database.reelDao(),
            database.statusDao()
        )
    }
}
