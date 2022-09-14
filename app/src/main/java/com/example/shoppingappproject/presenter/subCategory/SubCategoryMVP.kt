package com.example.shoppingappproject.presenter.subCategory

import com.example.shoppingappproject.model.remote.data.subcategory.SubCategoryResponse

interface SubCategoryMVP {
    interface SubCategoryPresenter {
        fun getSubCategory(categoryId: String): String
    }

    interface SubCategoryView {
        fun setResult(subCategoryResponse:SubCategoryResponse?)
        fun onLoad(isLoading: Boolean)
    }
}