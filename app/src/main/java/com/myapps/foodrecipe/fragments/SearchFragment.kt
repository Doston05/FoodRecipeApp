package com.myapps.foodrecipe.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.myapps.foodrecipe.Constants
import com.myapps.foodrecipe.activities.MainActivity
import com.myapps.foodrecipe.activities.MealActivity
import com.myapps.foodrecipe.adapters.MealsAdapter
import com.myapps.foodrecipe.databinding.FragmentSearchBinding
import com.myapps.foodrecipe.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding:FragmentSearchBinding?=null
    val binding get() = _binding!!
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchRvAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgSearchArrow.setOnClickListener { searchMeals() }
        searchMeals()
        prepareRv()
        observeSearchMealsLiveData()
        onItemClick()
       searchTextListener()
    }
    private fun searchTextListener(){
        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener{searchQuery->
            searchJob?.cancel()
            searchJob =lifecycleScope.launch{
                delay(1000)
                viewModel.searchMeals(searchQuery.toString())
            }

        }
    }

    private fun observeSearchMealsLiveData() {
        viewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner) { mealsList ->
            searchRvAdapter.differ.submitList(mealsList)

        }
    }

    private fun searchMeals() {
        val searchQuery=binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }
    private fun onItemClick(){
        searchRvAdapter.onItemClick={
                meal->
            val intent= Intent(activity, MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID,meal.idMeal)
            intent.putExtra(Constants.MEAL_NAME,meal.strMeal)
            intent.putExtra(Constants.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun prepareRv(){
        searchRvAdapter= MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRvAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}