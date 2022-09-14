package com.example.shoppingappproject.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.model.remote.data.productDetails.Parameters
import com.example.shoppingappproject.R

class ParametersAdapter(
    val parametersArrayList:ArrayList<Parameters>)
    :RecyclerView.Adapter<ParametersAdapter.ParametersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParametersViewHolder {
        val parametersView:View = LayoutInflater.from(parent.context).inflate(R.layout.view_product_parameters, parent, false)
        return ParametersViewHolder(parametersView)

    }

    override fun getItemCount() = parametersArrayList.size

    override fun onBindViewHolder(holder: ParametersViewHolder, position: Int) {
        holder.apply{
            val list = parametersArrayList.get(position)
            txtParameterTitle.text = list.title
            txtParameterDetails.text = list.details
        }
    }



    inner class ParametersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtParameterTitle : TextView = view.findViewById(R.id.txt_parameter_title)
        val txtParameterDetails :TextView = view.findViewById(R.id.txt_parameter_details)
    }

}
