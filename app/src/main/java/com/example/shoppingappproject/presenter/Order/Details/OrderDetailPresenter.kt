package com.example.shoppingappproject.presenter.Order.Details

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.order.orderDetail.OrderDetailResponse

class OrderDetailPresenter(
    private var volleyHandler: VolleyHandler,
    private var loginView:OrderDetailMVP.OrderDetailsView)
    :OrderDetailMVP.OrderDetailsPresenter{

    override fun getOrderDetails(orderId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getOrderDetailFromApi(orderId, object
            : OperationalCallback {
            override fun onSuccess(data: Any) {
                loginView.onLoad(false)
                loginView.setResult(data as OrderDetailResponse)

            }

            override fun onFailure(message: String) {
                loginView.onLoad(false)
                loginView.setResult(null)
            }
        })
        return message

    }
}