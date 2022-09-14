package com.example.shoppingappproject.presenter.productDetail

import com.example.shoppingappproject.model.remote.data.productDetails.ProductDetailResponse

interface ProductDetailsMVP {

    interface ProductDetailsView {
        fun setResult(productDetailResponse: ProductDetailResponse?)
        fun onLoad(isLoading: Boolean)

    }

    interface ProductDetailsPresenter {
        fun getProductDetails(productId: String): String
    }
}