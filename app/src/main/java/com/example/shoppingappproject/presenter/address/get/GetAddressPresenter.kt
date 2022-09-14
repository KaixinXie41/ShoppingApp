package com.example.shoppingappproject.presenter.address.get

import com.example.shoppingappproject.model.remote.data.address.AddressResponse
import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler

class GetAddressPresenter (
    private val volleyHandler: VolleyHandler,
    private val loginView: GetAddressMVP.GetAddressView)
    : GetAddressMVP.GetAddressPresenter {

    override fun getAddress(userId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getAddressFromApi(userId, object : OperationalCallback {
            override fun onSuccess(data: Any) {
                loginView.onLoad(false)
                loginView.setResult(data as AddressResponse)
            }

            override fun onFailure(message: String) {
                loginView.onLoad(false)
                loginView.setResult(null)
            }
        })
        return message
    }
}