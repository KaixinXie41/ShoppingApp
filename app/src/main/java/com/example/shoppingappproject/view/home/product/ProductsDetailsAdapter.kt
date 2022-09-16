package com.example.shoppingappproject.view.home.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.remote.Constants.BASE_IMAGE_URL
import com.example.shoppingappproject.model.remote.data.productdetails.Image

class ProductsDetailsAdapter(
    private val context: Context,
    private val imageList: ArrayList<Image>)
    :RecyclerView.Adapter<ProductsDetailsAdapter.ProductDetailsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_product_image, parent, false)
        return ProductDetailsViewHolder(view)

    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: ProductDetailsViewHolder, position: Int) {
        holder.apply {
            Glide.with(context)
                .load(BASE_IMAGE_URL +imageList[position+1].image)
                .into(imageProduct)
        }
    }

    inner class ProductDetailsViewHolder(private val view:View):RecyclerView.ViewHolder(view) {
       val imageProduct :ImageView = view.findViewById(R.id.imgProductDetails)
    }
}