package com.example.store.data.local.mapper

import com.example.store.data.local.entity.ProductEntity
import com.example.store.domain.model.Product


fun ProductEntity.toModel(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating
)

fun List<ProductEntity>.toModels(): List<Product> = this.map { it.toModel() }

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating
)