package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isPremium: Boolean = false,
    val profilePicUrl: String? = null
)

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isGroup: Boolean = false,
    val latestMessage: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val profilePicUrl: String? = null
)

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val chatId: String,
    val senderId: String,
    val text: String? = null,
    val mediaUrl: String? = null,
    val mediaType: MediaType = MediaType.TEXT,
    val timestamp: Long = System.currentTimeMillis(),
    val isViewOnce: Boolean = false,
    val isDeletedForEveryone: Boolean = false
)

enum class MediaType {
    TEXT, IMAGE, VIDEO, AUDIO, DOCUMENT
}

@Entity(tableName = "reels")
data class Reel(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val uploaderId: String,
    val videoUrl: String,
    val description: String
)

@Entity(tableName = "statuses")
data class Status(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val uploaderId: String,
    val mediaUrl: String,
    val timestamp: Long = System.currentTimeMillis()
)
