package com.example.shoppingappproject.presenter.products


import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.products.ProductsResponse
import com.example.shoppingappproject.presenter.products.ProductsMVP


class ProductsPresenter(
    private val volleyHandler: VolleyHandler,
    private val productsView: ProductsMVP.ProductsView
) : ProductsMVP.ProductsPresenter {

    override fun getProducts(subCategoryId: String): String {
        productsView.onLoad(true)
        val message = volleyHandler.getProductFromApi(subCategoryId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    productsView.onLoad(false)
                    productsView.setResult(data as ProductsResponse)
                }

                override fun onFailure(message: String) {
                    productsView.onLoad(false)
                    productsView.setResult(null)
                }
            })
        return message
    }

    override fun searchProducts(query: String): String {
        productsView.onLoad(true)
        val message = volleyHandler.searchProductFromApi(query,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    productsView.onLoad(false)
                    productsView.setResult(data as ProductsResponse)
                }

                override fun onFailure(message: String) {
                    productsView.onLoad(false)
                    productsView.setResult(null)
                }
            })
        return message
    }
}