package com.example.shoppingappproject.view.home.subcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.FragmentSubCategoryBinding
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.subcategory.SubCategoryResponse
import com.example.shoppingappproject.model.remote.data.subcategory.Subcategory
import com.example.shoppingappproject.presenter.subcategory.SubCategoryMVP
import com.example.shoppingappproject.presenter.subcategory.SubCategoryPresenter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayoutMediator

class SubCategoryFragment:Fragment(), SubCategoryMVP.SubCategoryView {

    private val args:SubCategoryFragmentArgs by navArgs()
    private lateinit var binding: FragmentSubCategoryBinding
    private lateinit var subCategoryPresenter: SubCategoryPresenter
    private lateinit var subCategoryList: ArrayList<Subcategory>
    private lateinit var categoryId: String
    lateinit var currentView: View



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        subCategoryPresenter = SubCategoryPresenter(VolleyHandler(view.context), this)
        categoryId = args.categoryId
        subCategoryPresenter.getSubCategory(categoryId)
    }

    override fun setResult(subCategoryResponse: SubCategoryResponse?) {
        subCategoryResponse?.let {
            subCategoryList = subCategoryResponse.subcategories
            val adapter = SubViewPageAdapter(this, subCategoryList)
            currentView.findViewById<ViewPager2>(R.id.sub_cat_viewPage2).adapter = adapter
            TabLayoutMediator(
                currentView.findViewById(R.id.sub_cat_tab),
                currentView.findViewById(R.id.sub_cat_viewPage2)
            ) { tab, position ->
                tab.text = subCategoryList[position].subcategory_name
            }.attach()
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


