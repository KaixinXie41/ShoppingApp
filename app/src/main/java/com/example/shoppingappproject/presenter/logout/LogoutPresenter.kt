package com.example.shoppingappproject.presenter.logout

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler


class LogoutPresenter (

    private val volleyHandler:VolleyHandler,
    private val loginView: LogoutMVP.LogoutView
):LogoutMVP.LogoutPresenter {

    override fun userLogout(email: String): String {
        val message = volleyHandler.userLogout(email,
            object : OperationalCallback {
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