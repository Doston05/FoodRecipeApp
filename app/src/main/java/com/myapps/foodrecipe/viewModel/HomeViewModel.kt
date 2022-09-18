package com.myapps.foodrecipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.foodrecipe.db.MealDatabase
import com.myapps.foodrecipe.pojo.*
import com.myapps.foodrecipe.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response

class HomeViewModel (
    private val mealDatabase: MealDatabase
        ): ViewModel() {
    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealsLiveData=mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData=MutableLiveData<Meal>()
    private val searchMealsLiveData=MutableLiveData<List<Meal>>()


    var saveStateRandomMeal:Meal?=null
     fun getRandomMeal(){
        saveStateRandomMeal?.let { randomMeal->
            randomMealLiveData.postValue(randomMeal)

        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    saveStateRandomMeal=randomMeal
                    randomMealLiveData.value=randomMeal
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
            }

        })
    }
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {

                if (response.body()!=null){
                    popularItemsLiveData.value=response.body()!!.meals
                }

            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("MainActivity", "onFailure:${t.message} ")            }

        })
    }
    fun observerPopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData

    }
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }         }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure:${t.message} ")            }
        })
    }
    fun observeCategoryLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }
    fun observeFavoriteMealsLiveData():LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }
    fun deleteMeal(meal:Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal )
        }
    }
    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.meals?.first()?.let {
                    bottomSheetMealLiveData.postValue(it)
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure:${t.message} ")            }

        })


    }
    fun searchMeals(searchQuery:String)=RetrofitInstance.api.searchMeals(searchQuery).enqueue(object : Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealsList= response.body()?.meals
           mealsList.let {
                searchMealsLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.e("HomeViewModel", "onFailure:${t.message} ")        }

    })
    fun observeSearchMealsLiveData():LiveData<List<Meal>> = searchMealsLiveData
    fun observerBottomSheetMeal():LiveData<Meal> = bottomSheetMealLiveData

}