package com.example.shoppingappproject.presenter.Order.Details

import com.example.shoppingappproject.model.remote.data.order.Get.OrderResponse
import com.example.shoppingappproject.model.remote.data.order.orderDetail.OrderDetailResponse

interface OrderDetailMVP {

    interface OrderDetailsView{
        fun onLoad(isLoading:Boolean)
        fun setResult(orderDetailsResponse: OrderDetailResponse?)

    }

    interface OrderDetailsPresenter{
        fun getOrderDetails(orderId:String):String
    }
}