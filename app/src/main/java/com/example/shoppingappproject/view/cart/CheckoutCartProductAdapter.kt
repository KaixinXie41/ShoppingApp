package com.example.shoppingappproject.view.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.local.CartProduct
import com.example.shoppingappproject.model.remote.Constants.BASE_IMAGE_URL

class CheckoutCartProductAdapter(
    private val context: Context,
    val productArrayList:ArrayList<CartProduct>
) :RecyclerView.Adapter<CheckoutCartProductAdapter.CartProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.view_purchase_item, parent, false )
        return CartProductViewHolder(view)
    }

    override fun getItemCount() = productArrayList.size

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.apply {
            val list = productArrayList.get(position)
            cartProductName.text = list.productName
            unitPrice.text = "${list.price}"
            unitQuantity.text = list.count.toString()
            productTotalPrice.text = (list.count *list.price).toString()
            Log.e("product_img_url", "$BASE_IMAGE_URL${list.productImageUrl}")

            Glide.with(context)
                .load("$BASE_IMAGE_URL${list.productImageUrl}")
                .into(cartProduct)

        }
    }

    inner class CartProductViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val cartProduct:ImageView = view.findViewById(R.id.imgCart)
        val cartProductName:TextView = view.findViewById(R.id.txt_product_name)
        val unitPrice:TextView = view. findViewById(R.id.txt_unit_Price_value)
        val unitQuantity:TextView = view. findViewById(R.id.txt_Quantity_value)
        val productTotalPrice :TextView = view.findViewById(R.id.txt_total_price_value)
    }
}