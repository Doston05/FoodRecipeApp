 package com.myapps.foodrecipe.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapps.foodrecipe.R
import com.myapps.foodrecipe.db.MealDatabase
import com.myapps.foodrecipe.viewModel.HomeViewModel
import com.myapps.foodrecipe.viewModel.HomeViewModelFactory

 class MainActivity : AppCompatActivity() {
      val viewModel:HomeViewModel by lazy {
          val mealDatabase=MealDatabase.getInstance(this)
          val homeViewModelFactory=HomeViewModelFactory(mealDatabase)
          ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
      }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation=findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController=Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}