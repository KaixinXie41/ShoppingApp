package com.example.shoppingappproject.view.cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.model.local.CartProduct

class CartFragment : Fragment() {

    private lateinit var adapter: CartAdapter
    private lateinit var cartProductList: ArrayList<CartProduct>
    lateinit var currentView: View
    private lateinit var cartDao: CartDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        cartDao = CartDao(view.context)
        cartProductList = cartDao.getAllCartProduct()
        var total = 0.0
        for (i in 0 until cartProductList.size) {
            val cartProduct = cartProductList[i]
            total += cartProduct.price * cartProduct.count
        }
        view.findViewById<TextView>(R.id.tv_cart_fragment_total_amount).text =
            total.toString()
        adapter = CartAdapter(view.context, cartProductList)
        currentView.findViewById<RecyclerView>(R.id.recycleView_Cart_list).layoutManager = LinearLayoutManager(view.context)
        currentView.findViewById<RecyclerView>(R.id.recycleView_Cart_list).adapter = adapter

        val btnPlaceOrder: Button = view.findViewById(R.id.btn_place_order)
        btnPlaceOrder.setOnClickListener {
            val action = CartFragmentDirections.actionPlaceOrder()
            currentView.findNavController().navigate(action)
        }

    }
}