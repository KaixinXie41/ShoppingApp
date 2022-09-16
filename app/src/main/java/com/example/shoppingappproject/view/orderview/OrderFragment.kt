package com.example.shoppingappproject.view.orderview

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.order.get.Order
import com.example.shoppingappproject.model.remote.data.order.get.OrderResponse
import com.example.shoppingappproject.presenter.order.get.GetOrderMVP
import com.example.shoppingappproject.presenter.order.get.GetOrderPresenter
import com.example.shoppingappproject.view.other.LoginActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlin.collections.ArrayList

class OrderFragment : Fragment(), GetOrderMVP.GetOrderView {

    private lateinit var adapter: OrderAdapter
    private lateinit var orderList: ArrayList<Order>
    private lateinit var presenter: GetOrderPresenter
    private lateinit var currentView: View
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = GetOrderPresenter(VolleyHandler(view.context), this)
        sharedPreferences = this.requireActivity().getSharedPreferences(
            LoginActivity.Account_Information,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()

        val userId = sharedPreferences.getString(LoginActivity.USER_ID, "-1")
        userId?.let {
            presenter.getOrders(userId)
        }
    }

    @SuppressLint("CutPasteId")
    override fun setResult(orderResponse: OrderResponse?) {
        orderResponse?.let {
            orderList = orderResponse.orders
            adapter = OrderAdapter(currentView.context, orderList)
            currentView.findViewById<RecyclerView>(R.id.recyclerView_order).layoutManager =
                LinearLayoutManager(currentView.context)
            currentView.findViewById<RecyclerView>(R.id.recyclerView_order).adapter = adapter

        }
    }

    override fun onLoad(isLoading: Boolean) {
        if (isLoading) {
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility =
                View.VISIBLE
        } else {
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility =
                View.GONE
        }

    }
}