package com.example.shoppingappproject.view.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.model.remote.data.address.Address
import com.example.shoppingappproject.R


class AddressAdapter(
    private val context:Context,
    val addressArrayList:ArrayList<Address>)
    :RecyclerView.Adapter<AddressAdapter.AddressViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.view_address,parent,false)
        return AddressViewHolder(view)
    }

    override fun getItemCount() =addressArrayList.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.apply {
            val list = addressArrayList[position]
            addressValue.text = list.address
            addressTitle.text = list.title

            itemView.setOnClickListener{
                Log.e("address_id", list.address_id)
            }
        }

    }
    inner class AddressViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val addressValue: TextView = view.findViewById(R.id.txt_address)
        val addressTitle:TextView = view. findViewById(R.id.txt_address_title)
    }
}