package com.example.shoppingappproject.view.home.homepage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.FragmentHomePageBinding
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.category.Category
import com.example.shoppingappproject.model.remote.data.category.CategoryResponse
import com.example.shoppingappproject.presenter.category.CategoryMVP
import com.example.shoppingappproject.presenter.category.CategoryPresenter
import com.example.shoppingappproject.view.home.SearchFragment
import com.google.android.material.progressindicator.CircularProgressIndicator

class HomePageFragment : Fragment(),CategoryMVP.CategoryView {
    private lateinit var binding:FragmentHomePageBinding
    private lateinit var presenter: CategoryPresenter
    lateinit var adapter: HomeAdapter
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var currentView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = CategoryPresenter(VolleyHandler(view.context), this)
        presenter.getCategory()


    }

    @SuppressLint("CutPasteId")
    override fun setResult(categoryResponse: CategoryResponse?) {
        categoryResponse?.let {
            categoryList = categoryResponse.categories
            currentView.let {
                adapter = HomeAdapter(currentView.context, categoryList)
                currentView.findViewById<RecyclerView>(R.id.recyclerView_home).layoutManager = GridLayoutManager(currentView.context, 2)
                currentView.findViewById<RecyclerView>(R.id.recyclerView_home).adapter = adapter

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.btnSearch){
            val action = HomePageFragmentDirections.actionSearch()
            findNavController().navigate(action)
            return true
        }
        return super.onOptionsItemSelected(item)
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
