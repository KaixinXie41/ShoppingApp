package com.example.shoppingappproject.model.remote

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OperationalCallback {
    fun onSuccess(data:Any)
    fun onFailure(message:String)
}