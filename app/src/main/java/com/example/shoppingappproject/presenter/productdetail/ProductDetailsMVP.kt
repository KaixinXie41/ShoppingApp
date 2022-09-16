package com.example.shoppingappproject.presenter.productdetail

import com.example.shoppingappproject.model.remote.data.productdetails.ProductDetailResponse

interface ProductDetailsMVP {

    interface ProductDetailsView {
        fun setResult(productDetailResponse: ProductDetailResponse?)
        fun onLoad(isLoading: Boolean)

    }

    interface ProductDetailsPresenter {
        fun getProductDetails(productId: String): String
    }
}