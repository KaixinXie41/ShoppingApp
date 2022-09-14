package com.example.shoppingappproject.model.remote.data.address

import com.example.shoppingappproject.model.remote.data.address.Address

data class AddressResponse(
    val addresses: ArrayList<Address>,
    val message: String,
    val status: Int
)