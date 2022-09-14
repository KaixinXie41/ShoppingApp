package com.example.shoppingappproject.presenter.logout

interface LogoutMVP {
    interface LogoutView{
        fun onLoad(isLoading:Boolean)
        fun setResult(message:String)
    }

    interface LogoutPresenter{
        fun userLogout(email:String):String
    }
}