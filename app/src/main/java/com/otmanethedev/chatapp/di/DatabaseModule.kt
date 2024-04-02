package com.otmanethedev.chatapp.di

import android.content.Context
import androidx.room.Room
import com.otmanethedev.chatapp.data.local.MessagesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMessagesDatabase(@ApplicationContext context: Context): MessagesDatabase {
        return Room
            .databaseBuilder(context, MessagesDatabase::class.java, "messages_db")
            .createFromAsset("database/messages")
            .build()
    }

    @Provides
    @Singleton
    fun provideMessagesDao(messagesDatabase: MessagesDatabase) = messagesDatabase.messagesDao()
}
