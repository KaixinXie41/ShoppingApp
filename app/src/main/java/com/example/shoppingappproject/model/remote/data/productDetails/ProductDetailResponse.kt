package com.example.shoppingappproject.model.remote.data.productDetails

import com.example.shoppingappproject.model.remote.data.productDetails.Product

data class ProductDetailResponse(
    val message: String,
    val product: Product,
    val status: Int
)