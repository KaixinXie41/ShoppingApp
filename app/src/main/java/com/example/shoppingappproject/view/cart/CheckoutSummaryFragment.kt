package com.example.shoppingappproject.view.cart

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.model.local.CartProduct
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.order.Place.DeliveryAddress
import com.example.shoppingappproject.model.remote.data.order.Place.Item
import com.example.shoppingappproject.model.remote.data.order.Place.PlaceOrderRequest
import com.example.shoppingappproject.presenter.Order.Place.PlaceOrderMVP
import com.example.shoppingappproject.presenter.Order.Place.PlaceOrderPresenter
import com.example.shoppingappproject.view.Other.LoginActivity
import com.example.shoppingappproject.view.cart.CheckoutDeliveryFragment.Companion.ADDRESS
import com.example.shoppingappproject.view.cart.CheckoutDeliveryFragment.Companion.ADDRESS_TITLE
import com.example.shoppingappproject.view.cart.CheckoutPaymentFragment.Companion.PAYMENT
import com.google.android.material.progressindicator.CircularProgressIndicator


class CheckoutSummaryFragment : Fragment(), PlaceOrderMVP.PlaceOrderView {

    private lateinit var adapter: CheckoutCartProductAdapter
    private lateinit var cartProductList: ArrayList<CartProduct>
    private lateinit var presenter: PlaceOrderPresenter
    private lateinit var cartDao: CartDao
    private lateinit var currenView:View
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout_summary, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currenView = view
        presenter = PlaceOrderPresenter(VolleyHandler(view.context),this)
        cartDao = CartDao(view.context)
        cartProductList= cartDao.getAllCartProduct()
        sharedPreferences = this.requireActivity().getSharedPreferences(
            LoginActivity.Account_Information,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()

        var total = 0.0
        for(i in 0 until cartProductList.size){
            val cartProduct = cartProductList[i]
            total += cartProduct.price * cartProduct.count
        }
        view.findViewById<TextView>(R.id.txt_summary_total_bill_amount_value).text = total.toString()

        adapter = CheckoutCartProductAdapter(view.context, cartProductList)
        currenView.findViewById<RecyclerView>(R.id.recyclerView_summary_cart).layoutManager =
            LinearLayoutManager(view.context)
        currenView.findViewById<RecyclerView>(R.id.recyclerView_summary_cart).adapter = adapter

        val payment = sharedPreferences.getString(PAYMENT,"None")
        val title = sharedPreferences.getString(ADDRESS_TITLE,"None")
        val address = sharedPreferences.getString(ADDRESS,"None")

        if(payment != null){
            view.findViewById<TextView>(R.id.txt_summary_total_bill_amount_value).text = payment
        }
        if(address != null && title != null){
            view.findViewById<TextView>(R.id.txt_summary_address_title).text = title
            view.findViewById<TextView>(R.id.txt_summary_address).text = address
        }

        val btnPlaceOrder : Button =view.findViewById(R.id.btn_summary_confirm_place)
        btnPlaceOrder.setOnClickListener{
            val userId = sharedPreferences.getString(LoginActivity.USER_ID, "-1")
            userId?.let{
                val cartProductList = ArrayList<CartProduct>()
                val itemList = ArrayList<Item>()
                var summaryTotal  = 0
                for(i in 0 until cartProductList.size){
                    val cartProduct = cartProductList[i]
                    summaryTotal += (cartProduct.price * cartProduct.count).toInt()
                    itemList.add(Item(
                        cartProduct.cartId!!.toInt(),
                        cartProduct.count,
                        cartProduct.price.toInt()
                    ))
                }
                if(payment != null && address != null && title != null){
                    val deliveryAddress = DeliveryAddress(address, title)
                    val placeOrderRequest = PlaceOrderRequest(summaryTotal,deliveryAddress,itemList,payment,userId.toInt())
                    presenter.placeOrder(placeOrderRequest)
                }
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.VISIBLE
        } else {
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.GONE
        }
    }

    override fun setResult(message: String?) {
        cartDao.clearTable()
        val action = CheckoutFragmentDirections.actionFinishOrder()
        currenView.findNavController().navigate(action)
    }
}