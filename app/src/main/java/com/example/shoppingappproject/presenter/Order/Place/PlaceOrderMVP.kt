package com.example.shoppingappproject.presenter.Order.Place

import com.example.shoppingappproject.model.remote.data.order.Place.PlaceOrderRequest

interface PlaceOrderMVP {

    interface PlaceOrderView {
        fun setResult(message: String?)
        fun onLoad(isLoading: Boolean)
    }
    interface PlaceOrderPresenter {
        fun placeOrder(placeOrderRequest: PlaceOrderRequest): String
    }

}