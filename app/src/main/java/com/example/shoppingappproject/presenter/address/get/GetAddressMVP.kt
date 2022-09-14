package com.example.shoppingappproject.presenter.address.get

import com.example.shoppingappproject.model.remote.data.address.AddressResponse

interface GetAddressMVP {
    interface GetAddressPresenter {
        fun getAddress(userId: String): String
    }

    interface GetAddressView {
        fun setResult(addressResponse: AddressResponse?)
        fun onLoad(isLoading: Boolean)
    }
}