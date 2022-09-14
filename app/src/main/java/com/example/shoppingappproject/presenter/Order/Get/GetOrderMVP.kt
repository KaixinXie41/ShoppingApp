package com.example.shoppingappproject.presenter.Order.Get

import com.example.shoppingappproject.model.remote.data.order.Get.OrderResponse

interface GetOrderMVP {

    interface GetOrderView{
        fun onLoad(isLoading:Boolean)
        fun setResult(orderResponse: OrderResponse?)
    }
    interface GetOrderPresenter{
        fun getOrders(userId:String):String
    }
}