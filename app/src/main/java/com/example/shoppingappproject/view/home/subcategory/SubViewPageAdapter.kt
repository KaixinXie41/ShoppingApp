package com.example.shoppingappproject.view.home.subcategory

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shoppingappproject.model.remote.data.subcategory.Subcategory

class SubViewPageAdapter(subCategoryFragment: SubCategoryFragment,
                         private val subCategoryList:ArrayList<Subcategory>)
    :FragmentStateAdapter(subCategoryFragment) {
    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    override fun createFragment(position: Int): Fragment {
        return SubProductFragment(subCategoryList[position].subcategory_id)
    }
}