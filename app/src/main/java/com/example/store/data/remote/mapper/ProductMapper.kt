package com.example.store.data.remote.mapper

import com.example.store.data.remote.response.ProductResponse
import com.example.store.data.remote.response.RatingResponse
import com.example.store.domain.model.Product
import com.example.store.domain.model.Rating


fun RatingResponse.toModel(): Rating = Rating(
    rate = rate,
    count = count
)

fun ProductResponse.toModel(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.toModel()
)

fun List<ProductResponse>.toModels(): List<Product> = this.map { it.toModel() }