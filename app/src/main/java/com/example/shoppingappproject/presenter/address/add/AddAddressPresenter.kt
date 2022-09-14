package com.example.shoppingappproject.presenter.address.add

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler

class AddAddressPresenter (
    private val volleyHandler:VolleyHandler,
    private val loginView:AddAddressMVP.AddAddressView
):AddAddressMVP.AddAddressPresenter{
    override fun addAddress(userId: String, title: String, address: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.addAddressToAPI(userId, title, address, object :OperationalCallback{
            override fun onSuccess(data: Any) {
                loginView.onLoad(false)
                loginView.setResult(data as String)
            }

            override fun onFailure(message: String) {
                loginView.onLoad(false)
                loginView.setResult(message)
            }
        })
        return message
    }
}