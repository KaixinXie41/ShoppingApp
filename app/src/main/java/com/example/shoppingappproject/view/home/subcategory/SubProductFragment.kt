package com.example.shoppingappproject.view.home.subcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.presenter.products.ProductsMVP
import com.example.shoppingappproject.presenter.products.ProductsPresenter
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.FragmentSubProductBinding
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.products.Product
import com.example.shoppingappproject.model.remote.data.products.ProductsResponse
import com.example.shoppingappproject.view.home.product.ProductsAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator

class SubProductFragment(val subCategoryId:String):Fragment(), ProductsMVP.ProductsView{

    private lateinit var binding : FragmentSubProductBinding
    private lateinit var presenter: ProductsPresenter
    private lateinit var productList:ArrayList<Product>
    private lateinit var adapter: ProductsAdapter
    private lateinit var currentView : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = ProductsPresenter(VolleyHandler(view.context), this)
        presenter.getProducts(subCategoryId)
    }


    override fun setResult(productsResponse: ProductsResponse?) {
        productsResponse?.let{
            productList = productsResponse.products
            currentView.let {
                adapter = ProductsAdapter(currentView.context, productList, 0)
                val rvProducts:RecyclerView = currentView.findViewById(R.id.recyclerView_sub_product)
                rvProducts.layoutManager = LinearLayoutManager(context)
                rvProducts.adapter = adapter
            }
        }
    }
    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar).visibility = View.VISIBLE
        }else{
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar).visibility = View.GONE
        }
    }
}
