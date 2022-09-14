package com.example.shoppingappproject.view.cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.model.local.CartProduct

class CheckOutCartProductFragment : Fragment() {

    lateinit var adapter: CheckoutCartProductAdapter
    lateinit var cartProductList:ArrayList<CartProduct>
    lateinit var currentView: View
    private lateinit var cartDao:CartDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_check_out_cart_product, container, false)
    }
    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        cartDao = CartDao(view.context)
        cartProductList = cartDao.getAllCartProduct()
        var totalAmount: Double = 0.0
        for (i in 0 until cartProductList.size) {
            val cartItem = cartProductList.get(i)
            totalAmount = totalAmount + cartItem.price * cartItem.count
        }
        view.findViewById<TextView>(R.id.txt_total_amount_value).text =
            totalAmount.toString()
        adapter = CheckoutCartProductAdapter(view.context, cartProductList)
        currentView.findViewById<RecyclerView>(R.id.recyclerView_checkout_product).layoutManager =
            LinearLayoutManager(view.context)
        currentView.findViewById<RecyclerView>(R.id.recyclerView_checkout_product).adapter = adapter

        val btnCheckoutCartItemNext: Button = view.findViewById(R.id.btnNext)
        btnCheckoutCartItemNext.setOnClickListener {
            (this.parentFragment as CheckoutFragment).nextPager()
        }

    }

}