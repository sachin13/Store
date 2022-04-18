package com.example.store.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.store.base.BaseViewModel
import com.example.store.domain.model.Product
import com.example.store.domain.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : BaseViewModel() {

    private val _product = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>> = _product

    init {
        getCart()
    }

    private fun getCart() {
        viewModelScope.launch {
            storeRepository.getCart().collect {
                _product.value = it
            }
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            storeRepository.removeFromCart(product = product)
        }
    }

}