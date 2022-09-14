
package com.example.shoppingappproject.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.example.shoppingappproject.presenter.products.ProductsMVP
import com.example.shoppingappproject.presenter.products.ProductsPresenter
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.products.Product
import com.example.shoppingappproject.model.remote.data.products.ProductsResponse
import com.example.shoppingappproject.view.home.product.ProductFragment
import com.google.android.material.progressindicator.CircularProgressIndicator

class SearchFragment : Fragment(), ProductsMVP.ProductsView{

    private lateinit var productsList :ArrayList<Product>
    private lateinit var currenView:View
    private lateinit var presenter : ProductsPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currenView = view
        presenter = ProductsPresenter(VolleyHandler(currenView.context), this)
        view.findViewById<ImageButton>(R.id.imgBtnCancel).setOnClickListener{
            view.findViewById<EditText>(R.id.edtSearch).setText("")
        }
        view.findViewById<ImageButton>(R.id.imgBtnSearch).setOnClickListener{
            presenter.searchProducts(view.findViewById<EditText>(R.id.edtSearch).text.toString())
        }

    }

    override fun setResult(productsResponse: ProductsResponse?) {
        productsResponse?.let{
            productsList = productsResponse.products
            currenView.let {
                val productFragment = ProductFragment(productsList, 1)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout_search_view,productFragment)
                    .commit()
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgress)?.visibility = View.VISIBLE
        }
        else{
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgress)?.visibility = View.GONE
        }
    }

}