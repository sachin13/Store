package com.example.store.data.local.room

import androidx.room.*
import com.example.store.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {

    @Query("SELECT * FROM product")
    fun getCart(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(product: ProductEntity)

    @Delete
    suspend fun removeFromCart(product: ProductEntity)

}