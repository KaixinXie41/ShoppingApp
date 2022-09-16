package com.example.shoppingappproject.view.orderview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.order.orderDetail.OrderDetailResponse
import com.example.shoppingappproject.presenter.order.details.OrderDetailMVP
import com.example.shoppingappproject.presenter.order.details.OrderDetailPresenter
import com.google.android.material.progressindicator.CircularProgressIndicator

class OrderFragmentDetails : Fragment(), OrderDetailMVP.OrderDetailsView{

    private lateinit var presenter: OrderDetailPresenter
    private lateinit var currentView:View
    private lateinit var orderId:String
    private val args: OrderFragmentDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = OrderDetailPresenter(VolleyHandler(view.context), this)
        orderId = args.orderId
        presenter.getOrderDetails(orderId)

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

    @SuppressLint("SetTextI18n")
    override fun setResult(orderDetailsResponse: OrderDetailResponse?) {

        orderDetailsResponse?.let {
            val order = orderDetailsResponse.order
            currentView.let{
                Log.e("order",order.toString())
                val detailsOrderId:TextView = currentView.findViewById(R.id.txt_order_id_value)
                val detailsOrderStatus:TextView = currentView.findViewById(R.id.txt_order_status_value)
                val detailsOrderAmount:TextView = currentView.findViewById(R.id.txt_total_bill_amount_value)
                val deliveryTitle : TextView = currentView.findViewById(R.id.txt_address_title)
                val deliveryAddress : TextView = currentView.findViewById(R.id.txt_address)
                val detailsPayment :TextView = currentView.findViewById(R.id.txt_payment_info)

                detailsOrderId.text = "#"+order.order_id
                detailsOrderAmount.text = order.bill_amount
                detailsPayment.text = order.payment_method
                detailsOrderStatus.text = order.order_status
                deliveryTitle.text = order.address_title
                deliveryAddress.text = order.address

            }

        }
    }



}
