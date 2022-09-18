package com.myapps.foodrecipe.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.myapps.foodrecipe.Constants
import com.myapps.foodrecipe.activities.MainActivity
import com.myapps.foodrecipe.activities.MealActivity
import com.myapps.foodrecipe.adapters.MealsAdapter
import com.myapps.foodrecipe.databinding.FragmentFavoriteBinding
import com.myapps.foodrecipe.viewModel.HomeViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel:HomeViewModel
    private lateinit var favoritesAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteViewModel=(activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRv()
        observeFavorites()
        onFavoriteItemClicks()

        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val meal=favoritesAdapter.differ.currentList[position]
                favoriteViewModel.deleteMeal(favoritesAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo"

                ) {
                    favoriteViewModel.insertMeal(meal)
                }.show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

    private fun prepareRv() {
        favoritesAdapter=MealsAdapter()
        binding.rvFavorites.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favoritesAdapter
        }
    }

    private fun observeFavorites() {
        favoriteViewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner
        ) { meals ->
            favoritesAdapter.differ.submitList(meals)


        }
    }
    private fun onFavoriteItemClicks() {
        favoritesAdapter.onItemClick={
                meal->
            val intent= Intent(activity, MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID,meal.idMeal)
            intent.putExtra(Constants.MEAL_NAME,meal.strMeal)
            intent.putExtra(Constants.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
 
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}