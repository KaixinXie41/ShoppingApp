package com.example.shoppingappproject.model.local

object Constants {

    const val DB_NAME = "Shopping_App_DB"
    const val DB_VERSION = 1
    const val TABLE_NAME = "cartProduct"

    val CREATE_CART_TABLE = """CREATE TABLE cartProduct (
        cartId INTEGER PRIMARY KEY AUTOINCREMENT,
        productName TEXT,
        productId TEXT,
        description TEXT,
        price DOUBLE,
        categoryId TEXT,
        sbCategoryId TEXT,
        productImageUrl TEXT,
        count INTEGER
       
        )""".trimIndent()
}
