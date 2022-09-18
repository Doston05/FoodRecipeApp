package com.myapps.foodrecipe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapps.foodrecipe.db.MealDatabase

class MealsViewModelFactory(
    private val mealDatabase:MealDatabase

):ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        return MealViewModel(mealDatabase) as T
    }
}