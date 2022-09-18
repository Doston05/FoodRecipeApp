package com.myapps.foodrecipe.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.myapps.foodrecipe.Constants
import com.myapps.foodrecipe.activities.CategoryMealsActivity
import com.myapps.foodrecipe.activities.MainActivity
import com.myapps.foodrecipe.adapters.CategoriesAdapter
import com.myapps.foodrecipe.databinding.FragmentCategoryBinding
import com.myapps.foodrecipe.viewModel.HomeViewModel

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoriesAdapter:CategoriesAdapter
    private lateinit var viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        prepareRv()
        observeCategories()
        onCategoryClick()
    }

    private fun observeCategories() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)
        }
    }

    private fun prepareRv() {
        categoriesAdapter= CategoriesAdapter()
        binding.categoryRv.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }

    }
    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category ->
            val intent= Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(Constants.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}