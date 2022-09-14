package com.example.shoppingappproject.presenter.login

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.user.User

class LoginPresenter (
    private var volleyHandler: VolleyHandler,
    private var loginView:LoginMVP.LoginView)
    :LoginMVP.LoginPresenter {

    override fun userLogin(user: User): String {
        loginView.onLoad(true)
        val message = volleyHandler.userLogin(
            user,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as String)
                    loginView.setLogin(user)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(message)
                }
            })
        return message
    }
}

