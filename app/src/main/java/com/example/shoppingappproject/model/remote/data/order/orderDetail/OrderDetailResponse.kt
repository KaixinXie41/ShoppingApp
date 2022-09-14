package com.example.shoppingappproject.model.remote.data.order.orderDetail


data class OrderDetailResponse(
    val message: String,
    val order: Order,
    val status: Int
)