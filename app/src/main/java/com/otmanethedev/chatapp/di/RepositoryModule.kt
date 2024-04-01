package com.otmanethedev.chatapp.di

import com.otmanethedev.chatapp.data.repository.MessagesRepositoryImpl
import com.otmanethedev.chatapp.domain.repository.MessagesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRepository(repository: MessagesRepositoryImpl): MessagesRepository
}
