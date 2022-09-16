package com.example.shoppingappproject.presenter.productdetail

import com.example.shoppingappproject.model.remote.data.productdetails.ProductDetailResponse
import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler


class ProductDetailsPresenter(
    private val volleyHandler: VolleyHandler,
    private val loginView:ProductDetailsMVP.ProductDetailsView
):ProductDetailsMVP.ProductDetailsPresenter {
    override fun getProductDetails(productId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getProductsDetailFromApi(productId,
        object : OperationalCallback{

            override fun onSuccess(data: Any) {
                loginView.onLoad(false)
                loginView.setResult(data as ProductDetailResponse)
            }

            override fun onFailure(message: String) {
                loginView.onLoad(false)
                loginView.setResult(null)
            }
        })
        return message
    }
}