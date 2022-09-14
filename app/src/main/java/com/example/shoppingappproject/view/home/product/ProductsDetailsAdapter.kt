package com.example.shoppingappproject.view.home.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.remote.data.productDetails.Image

class ProductsDetailsAdapter(
    val currentView: View,
    private val imageList: ArrayList<Image>)
    :RecyclerView.Adapter<ProductsDetailsAdapter.ProductDetailsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_product_image, parent, false)
        return ProductDetailsViewHolder(view)

    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: ProductDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ProductDetailsViewHolder(private val view:View):RecyclerView.ViewHolder(view) {
        fun bind(position: Int){

            Glide.with(currentView)
                .load(com.example.shoppingappproject.model.remote.Constants.BASE_IMAGE_URL+imageList.get(position).image)
                .into(view.findViewById(R.id.imgProductDetails))
        }

    }
}