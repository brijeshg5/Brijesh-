package com.example.data

import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val chatDao: ChatDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
    private val reelDao: ReelDao,
    private val statusDao: StatusDao
) {
    val allChats: Flow<List<Chat>> = chatDao.getAllChats()
    val allUsers: Flow<List<User>> = userDao.getAllUsers()
    val allReels: Flow<List<Reel>> = reelDao.getAllReels()
    val allStatuses: Flow<List<Status>> = statusDao.getAllStatuses()

    fun getMessagesForChat(chatId: String): Flow<List<Message>> = messageDao.getMessagesForChat(chatId)

    suspend fun insertChat(chat: Chat) = chatDao.insertChat(chat)
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)
    suspend fun deleteMessageForEveryone(messageId: String) = messageDao.softDeleteMessage(messageId)
    
    suspend fun getUser(id: String): User? = userDao.getUserById(id)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    
    suspend fun insertReel(reel: Reel) = reelDao.insertReel(reel)
    suspend fun insertStatus(status: Status) = statusDao.insertStatus(status)
}
