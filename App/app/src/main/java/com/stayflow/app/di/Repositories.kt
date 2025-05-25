package com.stayflow.app.di

import com.stayflow.app.data.local.TokenStorageImpl
import com.stayflow.app.data.remote.SessionRepositoryImpl
import com.stayflow.app.domain.repository.SessionRepository
import com.stayflow.app.domain.repository.TokenStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface Repositories {
    @Binds
    @Singleton
    fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository


    @Binds
    @Singleton
    fun bindTokenStorageRepository(impl: TokenStorageImpl): TokenStorageRepository
}