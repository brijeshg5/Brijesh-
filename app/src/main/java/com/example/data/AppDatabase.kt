package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Chat::class, Message::class, Reel::class, Status::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
    abstract fun reelDao(): ReelDao
    abstract fun statusDao(): StatusDao
}
