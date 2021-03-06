package com.example.store.data.repository


import com.example.store.data.local.mapper.toEntity
import com.example.store.data.local.mapper.toModels
import com.example.store.data.local.room.StoreDao
import com.example.store.data.remote.api.StoreService
import com.example.store.data.remote.mapper.toModel
import com.example.store.data.remote.mapper.toModels
import com.example.store.data.repository.base.BaseRepository
import com.example.store.domain.model.Category
import com.example.store.domain.model.Product
import com.example.store.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.example.store.data.util.Resource
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeService: StoreService,
    private val storeDao: StoreDao
) : StoreRepository, BaseRepository() {

    override suspend fun getProducts(): Flow<Resource<List<Product>>> {
        return networkBoundResource(
            fetch = { apiCall { storeService.getProducts() } },
            map = { it.data!!.toModels().shuffled() }
        )
    }

    override suspend fun getDetailProduct(id: Int): Flow<Resource<Product>> {
        return networkBoundResource(
            fetch = { apiCall { storeService.getDetailProduct(idProduct = id) } },
            map = { it.data!!.toModel() }
        )
    }

    override suspend fun getProductCategories(): Flow<Resource<MutableList<Category>>> {
        return networkBoundResource(
            fetch = { apiCall { storeService.getProductCategories() } },
            map = {
                it.data!!.map { category ->
                    Category(
                        category = category,
                        selected = false
                    )
                }.shuffled().toMutableList()
            }
        )
    }

    override suspend fun getProductByCategory(category: String): Flow<Resource<List<Product>>> {
        return networkBoundResource(
            fetch = { apiCall { storeService.getProductByCategory(category = category) } },
            map = { it.data!!.toModels() }
        )
    }

    override suspend fun getCart(): Flow<List<Product>> = storeDao.getCart().map { it.toModels() }

    override suspend fun addToCart(product: Product) = withContext(coroutineContext) {
        storeDao.addToCart(product = product.toEntity())
    }

    override suspend fun removeFromCart(product: Product) = withContext(coroutineContext) {
        storeDao.removeFromCart(product = product.toEntity())
    }
}