package com.example.shoppingappproject.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.model.remote.data.productDetails.Specification
import com.example.shoppingappproject.R

class ParametersAdapter(
    val paraArrayList:ArrayList<Specification>)
    :RecyclerView.Adapter<ParametersAdapter.ParametersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParametersViewHolder {
        val parametersView:View = LayoutInflater.from(parent.context).inflate(R.layout.view_product_parameters, parent, false)
        return ParametersViewHolder(parametersView)

    }

    override fun getItemCount() = paraArrayList.size

    override fun onBindViewHolder(holder: ParametersViewHolder, position: Int) {
        holder.apply{
            val list = paraArrayList[position]
            txtParameterTitle.text = list.title
            txtParameterDetails.text = list.specification
        }
    }



    inner class ParametersViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtParameterTitle : TextView = view.findViewById(R.id.txt_parameter_title)
        val txtParameterDetails :TextView = view.findViewById(R.id.txt_parameter_details)
    }

}
