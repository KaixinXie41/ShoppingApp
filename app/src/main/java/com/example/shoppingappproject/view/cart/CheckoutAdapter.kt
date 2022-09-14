package com.example.shoppingappproject.view.cart

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CheckoutAdapter(
    checkoutFragment: CheckoutFragment,
    private val count:Int)
    :FragmentStateAdapter(checkoutFragment){

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CheckOutCartProductFragment()
            1 -> CheckoutDeliveryFragment()
            2 -> CheckoutPaymentFragment()
            3 -> CheckoutSummaryFragment()
            else -> CheckOutCartProductFragment()
        }
    }

    override fun getItemCount() = count

}

