package com.otmanethedev.chatapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otmanethedev.chatapp.data.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Query("SELECT * FROM messages ORDER BY date ASC ")
    fun getMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageEntity)
}