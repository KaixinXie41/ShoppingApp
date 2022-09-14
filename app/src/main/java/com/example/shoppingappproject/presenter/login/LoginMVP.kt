package com.example.shoppingappproject.presenter.login

import com.example.shoppingappproject.model.remote.data.user.User
interface LoginMVP {
    interface LoginView{
        fun setResult(message:String)
        fun onLoad(isLoading:Boolean)
        fun setLogin(user: User)
    }

    interface LoginPresenter{
        fun userLogin(user:User):String

    }
}