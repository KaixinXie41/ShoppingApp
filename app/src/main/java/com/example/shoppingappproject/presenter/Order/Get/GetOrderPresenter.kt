package com.example.shoppingappproject.presenter.Order.Get

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.order.Get.OrderResponse

class GetOrderPresenter (
    private val volleyHandler: VolleyHandler,
    private val loginView:GetOrderMVP.GetOrderView)
    :GetOrderMVP.GetOrderPresenter{
    override fun getOrders(userId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getOrdersFromApi(userId, object :OperationalCallback{
            override fun onSuccess(data: Any) {
                loginView.onLoad(false)
                loginView.setResult(data as OrderResponse)

            }

            override fun onFailure(message: String) {
                loginView.onLoad(false)
                loginView.setResult(null)
            }
        })
            return message
    }
}