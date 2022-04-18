package com.example.store.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.store.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun storeDao(): StoreDao
}