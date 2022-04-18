package com.example.store.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.store.base.BaseViewModel
import com.example.store.domain.model.Category
import com.example.store.domain.model.Product
import com.example.store.domain.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.example.store.data.util.Resource
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : BaseViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    private val _category = MutableLiveData<Resource<MutableList<Category>>>()
    val category: LiveData<Resource<MutableList<Category>>> = _category

    private val _indicatorCart = MutableLiveData(false)
    val indicatorCart: LiveData<Boolean> = _indicatorCart

    init {
        viewModelScope.launch {
            val initTask = async {
                getProductCategories()
                getProducts()
                getCart()
            }
            initTask.await()
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            storeRepository.getProducts().collect {
                _products.value = it
            }
        }
    }

    private fun getProductCategories() {
        viewModelScope.launch {
            storeRepository.getProductCategories().collect {
                _category.value = it.apply {
                    it.data?.add(0, Category(category = "all", selected = true))
                }
            }
        }
    }

    fun getProductByCategories(category: String) {
        viewModelScope.launch {
            storeRepository.getProductByCategory(category = category).collect {
                _products.value = it
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            storeRepository.addToCart(product = product)
        }
    }

    private fun getCart() {
        viewModelScope.launch {
            storeRepository.getCart().collect {
                _indicatorCart.value = it.isNotEmpty()
            }
        }
    }

}