package com.example.shoppingappproject.presenter.address.add

interface AddAddressMVP {

    interface AddAddressView{
        fun setResult(message: String)
        fun onLoad(isloading:Boolean)
    }

    interface AddAddressPresenter{
        fun addAddress(
            userId:String,
            title:String,
            address:String
        ):String
    }

}