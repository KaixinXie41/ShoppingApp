package com.example.shoppingappproject.view.home.homepage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.remote.Constants.BASE_IMAGE_URL
import com.example.shoppingappproject.model.remote.data.category.Category
import com.example.shoppingappproject.view.home.subcategory.SubCategoryFragment

class HomeAdapter(private val context: Context, val categoryList:ArrayList<Category>)
    :RecyclerView.Adapter<HomeAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_home, parent, false)
        return CategoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.apply {
            val list = categoryList[position]
            categoryName.text = list.category_name
            Glide.with(context)
                .load("$BASE_IMAGE_URL${list.category_image_url}")
                .into(imgHomePicture)
            itemView.setOnClickListener {
                Log.e("category_id", list.category_id)
                val action = HomePageFragmentDirections.actionNext(list.category_id)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount() = categoryList.size

    inner class CategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imgHomePicture: ImageView = view.findViewById(R.id.imgCategory)
        val categoryName: TextView = view.findViewById(R.id.txtCategoryName)
    }
}

