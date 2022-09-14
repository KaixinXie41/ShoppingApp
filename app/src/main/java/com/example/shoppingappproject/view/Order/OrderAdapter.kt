package com.example.shoppingappproject.view.Order

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.model.remote.data.order.Get.Order
import com.example.shoppingappproject.R

class OrderAdapter(
    private val context:Context,
    private val orderArrayList:ArrayList<Order>)
    :RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.view_order, parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount() = orderArrayList.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.apply {
            val list = orderArrayList[position]
            orderTitle.text = list.address_title
            orderAddress.text = list.address
            orderAmount.text = list.bill_amount
            orderDate.text = list.order_date
            orderStatus.text = list.order_status
            orderPayment.text = list.payment_method

            itemView.setOnClickListener{
                Log.e("order_id", list.order_id)
                val action =OrderFragmentDirections.actionOrderDetails()
                it.findNavController().navigate(action)
            }
        }
    }




    inner class OrderViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val orderTitle: TextView = view.findViewById(R.id.txt_order_title)
        val orderAddress:TextView = view.findViewById(R.id.txt_order_address)
        val orderAmount : TextView = view.findViewById(R.id.txt_order_amount)
        val orderStatus :TextView = view.findViewById(R.id.txt_order_status)
        val orderDate : TextView = view.findViewById(R.id.txt_order_date)
        val orderPayment:TextView = view. findViewById(R.id.txt_order_payment)
    }
}