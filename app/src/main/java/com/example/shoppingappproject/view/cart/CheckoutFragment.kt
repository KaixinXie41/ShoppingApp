package com.example.shoppingappproject.view.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.shoppingappproject.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment:Fragment() {

    lateinit var currentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view

            val adapter = CheckoutAdapter(this, 4)
        view.findViewById<ViewPager2>(R.id.viewpage_checkout).adapter = adapter
        val tabString = arrayOf("Cart Items", "Delivery", "Payment", "Summary")
        TabLayoutMediator(view.findViewById<TabLayout>(R.id.tabLayout_checkout), view.findViewById<ViewPager2>(R.id.viewpage_checkout)){ tab, position->
            tab.text = tabString[position]

        }.attach()

    }

    fun nextPager(){
        currentView.findViewById<ViewPager2>(R.id.viewpage_checkout).currentItem = currentView.findViewById<ViewPager2>(R.id.viewpage_checkout).currentItem + 1
    }
}