package com.example.shoppingappproject.view.home.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.FragmentSubProductBinding
import com.example.shoppingappproject.model.remote.data.products.Product

class ProductFragment(val productList:ArrayList<Product>, val from:Int) : Fragment() {

    private lateinit var productAdapter: ProductsAdapter
    private lateinit var binding: FragmentSubProductBinding
    private lateinit var currentView:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentSubProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("productList", "$productList")
        currentView = view
        productAdapter= ProductsAdapter(currentView.context, productList, from)
        currentView.findViewById<RecyclerView>(R.id.recyclerView_sub_product).layoutManager = LinearLayoutManager(currentView.context)
        currentView.findViewById<RecyclerView>(R.id.recyclerView_sub_product).adapter = productAdapter
    }

}
