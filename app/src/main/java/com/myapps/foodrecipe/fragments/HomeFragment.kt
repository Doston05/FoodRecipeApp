package com.myapps.foodrecipe.fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.myapps.foodrecipe.Constants.Companion.CATEGORY_NAME
import com.myapps.foodrecipe.Constants.Companion.MEAL_ID
import com.myapps.foodrecipe.Constants.Companion.MEAL_NAME
import com.myapps.foodrecipe.Constants.Companion.MEAL_THUMB
import com.myapps.foodrecipe.R
import com.myapps.foodrecipe.activities.CategoryMealsActivity
import com.myapps.foodrecipe.activities.MainActivity
import com.myapps.foodrecipe.activities.MealActivity
import com.myapps.foodrecipe.adapters.CategoriesAdapter
import com.myapps.foodrecipe.adapters.MostPopularMealsAdapter

import com.myapps.foodrecipe.databinding.FragmentHomeBinding
import com.myapps.foodrecipe.fragments.bottomsheet.MealBottomSheetFragment
import com.myapps.foodrecipe.pojo.MealsByCategory
import com.myapps.foodrecipe.pojo.Meal
import com.myapps.foodrecipe.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var randomMeal:Meal

    private val binding get() = _binding!!
    private lateinit var homeViewModel:HomeViewModel
    private lateinit var popularMealAdapter:MostPopularMealsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel=(activity as MainActivity).viewModel
        popularMealAdapter=MostPopularMealsAdapter()
        categoriesAdapter= CategoriesAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRv()
        homeViewModel.getRandomMeal()
        homeViewModel.getPopularItems()
        observerRandomMeal()
        observePopularItemsLiveData()
        onRandomMealClicks()
        onPopularItemClicks()
        prepareCategoriesRv()
        homeViewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()
        onPopularItemLongClick()
        onSearchIconClick()




    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick(){
        popularMealAdapter.onLongItemClick={
            val mealBottomSheetFragment=MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"meal info")

        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category ->
            val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRv() {
        binding.rvCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeViewModel.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
           categoriesAdapter.setCategoryList(categories)
        }
    }

    private fun onPopularItemClicks() {
        popularMealAdapter.onItemClick={
            meal->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }

    }

    private fun preparePopularItemsRv() {
        binding.rvPopularMeals.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularMealAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeViewModel.observerPopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList->
            popularMealAdapter.setMeals(mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClicks() {
        binding.randomMealCard.setOnClickListener {
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { t -> Glide.with(this@HomeFragment).load(t!!.strMealThumb).into(binding.imgRandomMeal)
        this.randomMeal=t
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}