package com.example.shoppingappproject.presenter.category

import com.example.shoppingappproject.model.remote.data.category.CategoryResponse

interface CategoryMVP {

    interface CategoryPresenter{
        fun getCategory():String

    }

    interface CategoryView{
        fun onLoad(isLoading:Boolean)
        fun setResult(categoryResponse: CategoryResponse?)
    }
}