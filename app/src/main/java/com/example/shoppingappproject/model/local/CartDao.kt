package com.example.shoppingappproject.model.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.shoppingappproject.model.local.Constants.TABLE_NAME

class CartDao(private val context: Context){
    private val dbHelper = DBHelper(context)
    private val db:SQLiteDatabase = dbHelper.writableDatabase

    fun addCart(cartProduct: CartProduct):Long{
        val contentValues = ContentValues()
        contentValues.apply {

            put("productName", cartProduct.productName)
            put("productId", cartProduct.productId)
            put("productImageUrl",cartProduct.productImageUrl)
            put("categoryId",cartProduct.categoryId)
            put("subCategoryId",cartProduct.subCategoryId)
            put("price",cartProduct.price)
            put("count",cartProduct.count)
            put("description",cartProduct.description)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateCartProduct(cartProduct: CartProduct):Boolean{
        val contentValues = ContentValues()
        contentValues.apply {
            put("cartId", cartProduct.cartId)
            put("productName", cartProduct.productName)
            put("productId", cartProduct.productId)
            put("productImageUrl",cartProduct.productImageUrl)
            put("categoryId",cartProduct.categoryId)
            put("subCategoryId",cartProduct.subCategoryId)
            put("price",cartProduct.price)
            put("count",cartProduct.count)
            put("description",cartProduct.description)
        }
        val numOfChange:Int = db.update(TABLE_NAME, contentValues, "cartId = ${cartProduct.cartId}", null)
        return numOfChange == 1
    }

    fun deleteCartProduct(cartId: Long):Boolean{
        val numOfChange : Int = db.delete(TABLE_NAME, "cartId = $cartId", null)
        return numOfChange == 1
    }

    fun clearTable() = db.execSQL("delete from $TABLE_NAME")

    @SuppressLint("Range")
    fun getCartProduct(cartId: Int):CartProduct?{
        val cursor : Cursor = db.query(TABLE_NAME, null,"cartId =?", arrayOf("$cartId"), null, null, null )
        if(cursor.moveToFirst()){
            val cartId = cursor.getLong(cursor.getColumnIndex("cartId"))
            val productName = cursor.getString(cursor.getColumnIndex("productName"))
            val productId = cursor.getString(cursor.getColumnIndex("productId"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val price = cursor.getDouble(cursor.getColumnIndex("price"))
            val categoryId = cursor.getString(cursor.getColumnIndex("categoryId"))
            val subCategoryId = cursor.getString(cursor.getColumnIndex("subCategoryId"))
            val productImageUrl = cursor.getString(cursor.getColumnIndex("productImageUrl"))
            val count = cursor.getInt(cursor.getColumnIndex("count"))
            val cartProduct = CartProduct(cartId, productName, productId, description, price, categoryId, subCategoryId, productImageUrl, count)
            return cartProduct
        }
        return null
    }
    @SuppressLint("Range")
    fun getAllCartProduct():ArrayList<CartProduct>{
        val cartProductList = ArrayList<CartProduct>()
        val cursor:Cursor = db.query(TABLE_NAME, null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
            val cartId = cursor.getLong(cursor.getColumnIndex("cartId"))
            val productName = cursor.getString(cursor.getColumnIndex("productName"))
            val productId = cursor.getString(cursor.getColumnIndex("productId"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val price = cursor.getDouble(cursor.getColumnIndex("price"))
            val categoryId = cursor.getString(cursor.getColumnIndex("categoryId"))
            val subCategoryId = cursor.getString(cursor.getColumnIndex("subCategoryId"))
            val productImageUrl = cursor.getString(cursor.getColumnIndex("productImageUrl"))
            val count = cursor.getInt(cursor.getColumnIndex("count"))
            cartProductList.add(CartProduct(cartId, productName, productId, description, price, categoryId, subCategoryId, productImageUrl, count))
            }while (cursor.moveToNext())
        }
        return cartProductList
    }

    @SuppressLint("Range")
    fun getCartProductByProductId(productId:Int):CartProduct?{
        val cursor : Cursor = db.query(TABLE_NAME, null,"productId =?", arrayOf("$productId"), null, null, null )
        if(cursor.moveToFirst()){
            val cartId = cursor.getLong(cursor.getColumnIndex("cartId"))
            val productName = cursor.getString(cursor.getColumnIndex("productName"))
            val productId = cursor.getString(cursor.getColumnIndex("productId"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val price = cursor.getDouble(cursor.getColumnIndex("price"))
            val categoryId = cursor.getString(cursor.getColumnIndex("categoryId"))
            val subCategoryId = cursor.getString(cursor.getColumnIndex("subCategoryId"))
            val productImageUrl = cursor.getString(cursor.getColumnIndex("productImageUrl"))
            val count = cursor.getInt(cursor.getColumnIndex("count"))
            val cartProduct = CartProduct(cartId, productName, productId, description, price, categoryId, subCategoryId, productImageUrl, count)
            return cartProduct
        }
        return null
    }


}