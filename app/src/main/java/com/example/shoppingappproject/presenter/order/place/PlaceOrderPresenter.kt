package com.example.shoppingappproject.presenter.order.place

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.order.place.PlaceOrderRequest

class PlaceOrderPresenter(
    private val volleyHandler: VolleyHandler,
    private val loginView: PlaceOrderMVP.PlaceOrderView)
    :PlaceOrderMVP.PlaceOrderPresenter {
    override fun placeOrder(placeOrderRequest: PlaceOrderRequest): String {
        loginView.onLoad(true)
        val message = volleyHandler.placeOrderToAPI(placeOrderRequest, object
            : OperationalCallback {
            override fun onSuccess(data: Any) {
                loginView.onLoad(false)
                loginView.setResult(data as String)
            }

            override fun onFailure(message: String) {
                loginView.onLoad(false)
                loginView.setResult(null)

            }
        })
        return message
    }
}