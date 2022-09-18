package com.myapps.foodrecipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.myapps.foodrecipe.Constants
import com.myapps.foodrecipe.Constants.Companion.CATEGORY_NAME
import com.myapps.foodrecipe.adapters.CategoryMealsAdapter
import com.myapps.foodrecipe.databinding.ActivityCategoryMealsBinding
import com.myapps.foodrecipe.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryViewModel:CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRv()
        onItemClicks()
        categoryViewModel= ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        categoryViewModel.getMealsByCategory(intent.getStringExtra(CATEGORY_NAME)!!)
        categoryViewModel.observeMealsLiveData().observe(this) { mealList ->
            categoryMealsAdapter.setMealsList(mealList)

        }
    }

    private fun prepareRv() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }


    }
    private fun onItemClicks() {
        categoryMealsAdapter.onItemClick={
                meal->
            val intent= Intent(this,MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID,meal.idMeal)
            intent.putExtra(Constants.MEAL_NAME,meal.strMeal)
            intent.putExtra(Constants.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }

    }

}