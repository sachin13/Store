package com.example.store.domain.repository

import com.example.store.domain.model.Product
import kotlinx.coroutines.flow.Flow
import com.example.store.domain.model.Category
import com.example.store.data.util.Resource

interface StoreRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
    suspend fun getDetailProduct(id: Int): Flow<Resource<Product>>
    suspend fun getProductCategories(): Flow<Resource<MutableList<Category>>>
    suspend fun getProductByCategory(category: String): Flow<Resource<List<Product>>>
    suspend fun getCart(): Flow<List<Product>>
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(product: Product)
}