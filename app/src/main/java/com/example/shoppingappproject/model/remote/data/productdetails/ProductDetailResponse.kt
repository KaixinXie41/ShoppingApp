package com.example.shoppingappproject.model.remote.data.productdetails

data class ProductDetailResponse(
    val message: String,
    val product: Product,
    val status: Int
)