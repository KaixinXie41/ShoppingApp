package com.example.shoppingappproject.model.local


data class CartProduct(
    var cartId: Int?,
    val productName: String,
    val productId: String,
    val description: String,
    val price: Double,
    val categoryId: String,
    val subCategoryId: String,
    val productImageUrl: String,
    var count: Int
)
