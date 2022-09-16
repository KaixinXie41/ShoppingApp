package com.example.shoppingappproject.presenter.order.place

import com.example.shoppingappproject.model.remote.data.order.place.PlaceOrderRequest

interface PlaceOrderMVP {

    interface PlaceOrderView {
        fun setResult(message: String?)
        fun onLoad(isLoading: Boolean)
    }
    interface PlaceOrderPresenter {
        fun placeOrder(placeOrderRequest: PlaceOrderRequest): String
    }

}