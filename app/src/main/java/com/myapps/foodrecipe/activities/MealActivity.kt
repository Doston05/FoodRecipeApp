package com.myapps.foodrecipe.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.JsonNull.INSTANCE
import com.myapps.foodrecipe.Constants.Companion.MEAL_ID
import com.myapps.foodrecipe.Constants.Companion.MEAL_NAME
import com.myapps.foodrecipe.Constants.Companion.MEAL_THUMB
import com.myapps.foodrecipe.R
import com.myapps.foodrecipe.databinding.ActivityMealBinding
import com.myapps.foodrecipe.db.MealDatabase
import com.myapps.foodrecipe.pojo.Meal
import com.myapps.foodrecipe.viewModel.MealViewModel
import com.myapps.foodrecipe.viewModel.MealsViewModelFactory
import okhttp3.internal.tls.OkHostnameVerifier.INSTANCE
import java.lang.StringBuilder
import java.time.chrono.MinguoChronology.INSTANCE
import kotlin.reflect.KParameter

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealMVVM:MealViewModel
    private lateinit var urlYoutubeVideo:String
    private lateinit var mealToSave:Meal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory=MealsViewModelFactory(mealDatabase)
        mealMVVM=ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        setContentView(binding.root)
        getMealInformationFromIntent()
        loadingCase()
        mealMVVM.getMealDetail(mealId)
        observerMealDetailsLiveData()
        onYoutubeImageClick()
        setInformationToView()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let {
                mealMVVM.insertMeal(it)
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(urlYoutubeVideo))
            startActivity(intent)

        }
    }

    private fun observerMealDetailsLiveData() {
        mealMVVM.observerDetailsLiveData().observe(this
        ) { t ->
            mealToSave=t
            onResponseCase()
            "Category : ${t!!.strCategory}".also { binding.tvCategory.text = it }
            "Area : ${t.strArea}".also { binding.tvArea.text = it }
            binding.tvInstructions.text = t.strInstructions
            urlYoutubeVideo = t.strYoutube
            binding.tvIngredients.text=getMealIngredients(t)

        }
    }
    fun getMealIngredients(meal:Meal):String{
         var ingredients:StringBuilder=StringBuilder()
        var number=0

        if (meal.strIngredient1!!.isNotEmpty()){
                ingredients.append("$number. ${meal.strIngredient1} - ${meal.strMeasure1}\n")
                number++

            }
        if (meal.strIngredient2!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient2} - ${meal.strMeasure2}\n")
            number++

        }
        if (meal.strIngredient3!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient3} - ${meal.strMeasure3}\n")
            number++

        }
        if (meal.strIngredient4!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient4} - ${meal.strMeasure4}\n")
            number++

        }
        if (meal.strIngredient5!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient5} - ${meal.strMeasure5}\n")
            number++

        }
        if (meal.strIngredient6!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient6} - ${meal.strMeasure6}\n")
            number++

        }
        if (meal.strIngredient7!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient7} - ${meal.strMeasure7}\n")
            number++

        }
        if (meal.strIngredient8!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient8} - ${meal.strMeasure8}\n")
            number++

        }
        if (meal.strIngredient9!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient9} - ${meal.strMeasure9}\n")
            number++

        }
        if (meal.strIngredient10!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient10} - ${meal.strMeasure10}\n")
            number++

        }
        if (meal.strIngredient11!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient11} - ${meal.strMeasure11}\n")
            number++

        }
        if (meal.strIngredient12!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient12} - ${meal.strMeasure12}\n")
            number++

        }
        if (meal.strIngredient13!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient13} - ${meal.strMeasure13}\n")
            number++

        }
        if (meal.strIngredient14!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient14} - ${meal.strMeasure14}\n")
            number++

        }
        if (meal.strIngredient15!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient15} - ${meal.strMeasure15}\n")
            number++

        }
        if (meal.strIngredient16!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient16} - ${meal.strMeasure16}\n")
            number++

        }
        if (meal.strIngredient17!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient17} - ${meal.strMeasure17}\n")
            number++

        }
        if (meal.strIngredient18!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient18} - ${meal.strMeasure18}\n")
            number++

        }

        if (meal.strIngredient19!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient19} - ${meal.strMeasure19}\n")
            number++

        }
        if (meal.strIngredient20!!.isNotEmpty()){
            ingredients.append("$number. ${meal.strIngredient20} - ${meal.strMeasure20}\n")


        }
        return ingredients.toString()

    }

    private fun setInformationToView() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgDetailMeal)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent(){
        val intent=intent
        mealName=intent.getStringExtra(MEAL_NAME)!!
        mealThumb=intent.getStringExtra(MEAL_THUMB)!!
        mealId=intent.getStringExtra(MEAL_ID)!!


    }
    private fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnAddToFav.visibility=View.INVISIBLE
        binding.tvInstructions.visibility=View.INVISIBLE
        binding.tvArea.visibility=View.INVISIBLE
        binding.tvCategory.visibility=View.INVISIBLE
        binding.instructions.visibility=View.INVISIBLE
        binding.imgYoutube.visibility=View.INVISIBLE

    }
    private fun onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnAddToFav.visibility=View.VISIBLE
        binding.tvInstructions.visibility=View.VISIBLE
        binding.tvArea.visibility=View.VISIBLE
        binding.tvCategory.visibility=View.VISIBLE
        binding.instructions.visibility=View.VISIBLE
        binding.imgYoutube.visibility=View.VISIBLE

    }

}