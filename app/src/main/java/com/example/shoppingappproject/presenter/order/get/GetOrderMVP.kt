package com.example.shoppingappproject.presenter.order.get

import com.example.shoppingappproject.model.remote.data.order.get.OrderResponse

interface GetOrderMVP {

    interface GetOrderView{
        fun onLoad(isLoading:Boolean)
        fun setResult(orderResponse: OrderResponse?)
    }
    interface GetOrderPresenter{
        fun getOrders(userId:String):String
    }
}