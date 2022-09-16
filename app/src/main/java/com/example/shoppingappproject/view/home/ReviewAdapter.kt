package com.example.shoppingappproject.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.remote.data.productdetails.Review

class ReviewAdapter (val reviewArrayList:ArrayList<Review>)
    :RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.view_user_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount() = reviewArrayList.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.apply {
            val list = reviewArrayList[position]
            reviewUserName.text = list.fullName
            reviewDesc.text = list.review
            reviewTitle.text = list.reviewTitle
            ratingBar.rating = list.average_rating.toFloat()

        }
    }

    inner class ReviewViewHolder(view: View):RecyclerView.ViewHolder(view){
        val reviewUserName : TextView = view.findViewById(R.id.txtUserName)
        val reviewTitle:TextView = view.findViewById(R.id.txt_review_title)
        val reviewDesc :TextView = view.findViewById(R.id.txt_review)
        val ratingBar :RatingBar = view.findViewById(R.id.review_ratingBar)
    }
}