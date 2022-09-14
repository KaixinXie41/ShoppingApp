package com.example.shoppingappproject.presenter.category

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.category.CategoryResponse

class CategoryPresenter (
    private var volleyHandler: VolleyHandler,
    private var loginView: CategoryMVP.CategoryView)
    :CategoryMVP.CategoryPresenter{

    override fun getCategory(): String {
        loginView.onLoad(true)
        val message = volleyHandler.getCategoryByApi(
            object :OperationalCallback{
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as CategoryResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}