package com.example.shoppingappproject.view.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingappproject.databinding.ViewCartBinding
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.model.local.CartProduct
import com.example.shoppingappproject.model.remote.Constants.BASE_IMAGE_URL

class CartAdapter(private val context: Context,private val cartArrayList:ArrayList<CartProduct>)
    :RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    private lateinit var binding: ViewCartBinding
    private lateinit var cartDao:CartDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ViewCartBinding.inflate(layoutInflater,parent,false)
        cartDao = CartDao(parent.context)
        return CartViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.apply{
            val carts = cartArrayList[position]
            txtName.text = carts.productName
            txtDecs.text = carts.description
            txtPrice.text = carts.price.toString()
            txtTotalPrice.text = (carts.price * carts.count).toString()
            Log.e("product_image_url", BASE_IMAGE_URL+carts.productImageUrl)

            Glide.with(context)
                .load(BASE_IMAGE_URL + carts.productImageUrl)
                .into(imgCart)

            val cartDetails = cartDao.getCartProductByProductId(carts.productId.toInt())
            if(cartDetails!= null && cartDetails.count >0) {
                txtCount.text = cartDetails.count.toString()
            }

            btnSub.setOnClickListener{
                if(cartDetails != null){
                    if(cartDetails.count<2){
                    cartDetails.cartId?.let { item ->
                        if (cartDao.deleteCartProduct(item)) {
                            Log.e(
                                "Delete",
                                "Delete cart id= ${cartDetails.cartId} name = ${cartDetails.productName} success"
                            )
                            notifyItemRemoved(position)
                            cartArrayList.removeAt(position)
                            notifyItemRangeChanged(position, cartArrayList.size)
                        }
                    }
                }else {
                    cartDetails.count = cartDetails.count - 1
                    cartDao.updateCartProduct(cartDetails)
                    txtCount.text = cartDetails.count.toString()
                }
                }
            }
            btnAdd.setOnClickListener {
                if (cartDetails != null) {
                    cartDetails.count = cartDetails.count + 1
                    cartDao.updateCartProduct(cartDetails)
                    txtCount.text = cartDetails.count.toString()
                }
            }

            itemView.setOnClickListener{
                Log.e("product_id", carts.productId)
                val action = CartFragmentDirections.actionProductDetails(carts.productId)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return cartArrayList.size
    }

    inner class CartViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        val imgCart: ImageView = binding.imgCart
        val txtName: TextView = binding.txtProductName
        val txtDecs: TextView = binding.txtProductDesc
        val txtPrice: TextView = binding.txtPrice
        val txtTotalPrice: TextView = binding.txtTotalPrice
        val txtCount: TextView = binding.txtProductCount
        val btnAdd: ImageButton = binding.btnPlus
        val btnSub: ImageButton = binding.btnMin
        lateinit var imgUrl: String

    }
}