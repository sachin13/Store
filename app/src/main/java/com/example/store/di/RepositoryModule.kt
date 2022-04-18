package com.example.store.di

import com.example.store.data.local.room.StoreDao
import com.example.store.data.remote.api.StoreService
import com.example.store.data.repository.StoreRepositoryImpl
import com.example.store.domain.repository.StoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideStoreRepository(storeService: StoreService, storeDao: StoreDao): StoreRepository =
        StoreRepositoryImpl(storeService = storeService, storeDao = storeDao)

}