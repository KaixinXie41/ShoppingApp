package com.example.shoppingappproject.presenter.subCategory

import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.subcategory.SubCategoryResponse


class SubCategoryPresenter(
        private val volleyHandler: VolleyHandler,
        private val loginView: SubCategoryMVP.SubCategoryView
    ) : SubCategoryMVP.SubCategoryPresenter {

    override fun getSubCategory(categoryId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getSubCategoryFromApi(categoryId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as SubCategoryResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}