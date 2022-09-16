package com.example.shoppingappproject.presenter.order.details

import com.example.shoppingappproject.model.remote.data.order.orderDetail.OrderDetailResponse

interface OrderDetailMVP{

    interface OrderDetailsView{
        fun onLoad(isLoading:Boolean)
        fun setResult(orderDetailsResponse: OrderDetailResponse?)
    }

    interface OrderDetailsPresenter{
        fun getOrderDetails(orderId:String):String
    }
}