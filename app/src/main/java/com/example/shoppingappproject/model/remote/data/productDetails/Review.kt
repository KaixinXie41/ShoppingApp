package com.example.shoppingappproject.model.remote.data.productDetails

import android.media.Rating

data class Review(
    val fullName:String,
    val reviewTime:String,
    val reviewTitle:String,
    val review:String,
    val average_rating : String
)
