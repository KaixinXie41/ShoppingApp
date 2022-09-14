package com.example.shoppingappproject.model.remote.data.products

data class Product(
    val price:String,
    val category_name :String,
    val category_id:String,
    val product_id:String,
    val product_name:String,
    val sub_category_id:String,
    val sub_category_name:String,
    val description:String,
    val average_rating:String,
    val product_image_url:String
)
