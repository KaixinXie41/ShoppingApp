package com.example.shoppingappproject.view.cart

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.view.Other.LoginActivity


class CheckoutPaymentFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var cartDao: CartDao
    private lateinit var currentView:View
    private lateinit var payment:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        cartDao = CartDao(view.context)
        sharedPreferences = this.requireActivity().getSharedPreferences(
            LoginActivity.Account_Information,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        payment = "cod"
        val group = view.findViewById<RadioGroup>(R.id.radio_group_payment)
        group.setOnCheckedChangeListener { _: RadioGroup,
                                           _: Int ->
            val checkRadioButton = group.findViewById<RadioButton>(group.checkedRadioButtonId)
            checkRadioButton?.let {
                when (checkRadioButton.id) {
                    R.id.rtn_cod -> {
                        payment = "Cash on Delivery"
                    }
                    R.id.rtn_dc_cc -> {
                        payment = "Debit Card or Credit Card"
                    }
                    R.id.rtn_oba -> {
                        payment = "Online Bank Account"
                    }
                    R.id.rtn_paypal -> {
                        payment = "Paypal"
                    }
                    else -> {
                        payment = "Cash on Delivery"
                    }
                }
            }
        val btnNextStep: Button = currentView.findViewById(R.id.payment_btnNext)
            btnNextStep.setOnClickListener{
                editor.putString(PAYMENT, payment)
                editor.apply()
                (this.parentFragment as CheckoutFragment).nextPager()
            }
        }
    }
    companion object{
        const val PAYMENT ="payment"
    }
}