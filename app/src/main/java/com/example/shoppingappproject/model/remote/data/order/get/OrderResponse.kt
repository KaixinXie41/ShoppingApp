package com.example.shoppingappproject.model.remote.data.order.get

data class OrderResponse(
    val message: String,
    val orders: ArrayList<Order>,
    val status: Int
)