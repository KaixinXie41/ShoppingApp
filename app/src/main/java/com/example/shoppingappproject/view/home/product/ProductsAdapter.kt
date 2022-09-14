package com.example.shoppingappproject.view.home.product

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.ViewProductBinding
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.model.local.CartProduct
import com.example.shoppingappproject.model.remote.Constants.BASE_IMAGE_URL
import com.example.shoppingappproject.model.remote.data.products.Product
import com.example.shoppingappproject.view.home.SearchFragmentDirections
import com.example.shoppingappproject.view.home.subcategory.SubCategoryFragmentDirections

class ProductsAdapter(
    private val context: Context,
    private val productArrayList:ArrayList<Product>,
    val from:Int
):RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private lateinit var cartDao:CartDao
    private lateinit var binding:ViewProductBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ViewProductBinding.inflate(layoutInflater,parent,false)
        cartDao = CartDao(parent.context)
        return ProductsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.apply {
            val products = productArrayList[position]
            txtName.text = products.product_name
            txtDecs.text = products.description
            txtPrice.text = products.price
            rating.rating = products.average_rating.toFloat()
            Log.e("product_image_url", BASE_IMAGE_URL + products.product_image_url)

            Glide.with(context)
                .load(BASE_IMAGE_URL + products.product_image_url)
                .into(imgProduct)
            var cartDetails = cartDao.getCartProductByProductId(products.product_id.toInt())
            if(cartDetails != null && cartDetails.count >0){
                txtAdd.visibility = View.GONE
                layout.visibility = View.VISIBLE
                txtCount.text = cartDetails.count.toString()
            }
            btnSub.setOnClickListener {
                if(cartDetails!= null){
                    if(cartDetails!!.count <2){
                        cartDetails!!.count.let{ item ->
                            if(cartDao.deleteCartProduct(item.toInt())){
                                Log.e("Delete", "Delete cart id= ${cartDetails!!.cartId} name = ${cartDetails!!.productName} success" )
                            }
                        }
                        txtAdd.visibility = View.VISIBLE
                        layout.visibility = View.GONE
                    }else{
                        cartDetails!!.count = cartDetails!!.count - 1
                        cartDao.updateCartProduct(cartDetails!!)
                        txtCount.text = cartDetails!!.count.toString()
                    }
                }
            }
            btnAdd.setOnClickListener {
                if(cartDetails != null){
                    cartDetails!!.count = cartDetails!!.count+1
                    cartDao.updateCartProduct(cartDetails!!)
                    txtCount.text = cartDetails!!.count.toString()
                }
            }
            txtAdd.setOnClickListener {
                txtAdd.visibility = View.GONE
                layout.visibility = View.VISIBLE
                val cartProduct = CartProduct(
                    null,
                    products.product_name,
                    products.product_id,
                    products.description,
                    products.price.toDouble(),
                    products.category_id,
                    products.sub_category_id,
                    products.product_image_url,
                    1
                )
                cartProduct.cartId = cartDao.addCart(cartProduct)
                if (cartProduct.cartId !=null && cartProduct.cartId!! >0){
                    txtCount.text = "1"
                    cartDetails = cartDao.getCartProductByProductId(cartProduct.productId.toInt())
                }
            }
            itemView.setOnClickListener{ p0->
                Log.e("product_id", products.product_id)
                val bundle = Bundle()
                bundle.putString("product_id", products.product_id)
                if(from == 0){
                    val action = SubCategoryFragmentDirections.actionProductDetails(products.product_id)
                    view.findNavController().navigate(action)
                }else{
                    val action = SearchFragmentDirections.actionProductDetails(products.product_id)
                    view.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }
    inner class ProductsViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        val imgProduct : ImageView = binding.imgProduct
        val txtName: TextView = binding.txtProductName
        val txtDecs: TextView = binding.txtProductDesc
        val txtPrice : TextView = binding.txtPrice
        val rating:RatingBar = binding.ratingBar
        val layout: ConstraintLayout = binding.layoutAddToCart
        val btnAdd: ImageButton = binding.btnPlus
        val btnSub: ImageButton = binding.btnMin
        val txtAdd : TextView = binding.txtAddToCart
        val txtCount:TextView = binding.txtProductCount


    }
}