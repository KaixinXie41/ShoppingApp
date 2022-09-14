package com.example.shoppingappproject.presenter.registration

import com.example.shoppingappproject.model.remote.data.user.User

interface RegistrationMVP {
    interface  RegistrationView{
        fun setResult(message:String)
        fun onLoad(isLoading:Boolean)
        fun setLogin(user:User)
    }
    interface  RegistrationPresenter {
        fun registerUser(user: User):String

    }
}