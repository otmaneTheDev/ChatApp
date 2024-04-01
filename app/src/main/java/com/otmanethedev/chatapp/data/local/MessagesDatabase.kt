package com.otmanethedev.chatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.otmanethedev.chatapp.data.local.daos.MessagesDao
import com.otmanethedev.chatapp.data.local.entities.MessageEntity
import com.otmanethedev.chatapp.data.local.utils.Converters

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MessagesDatabase : RoomDatabase() {

    abstract fun messagesDao(): MessagesDao
}
