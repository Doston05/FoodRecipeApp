package com.myapps.foodrecipe.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myapps.foodrecipe.Constants.Companion.MEAL_ID
import com.myapps.foodrecipe.Constants.Companion.MEAL_NAME
import com.myapps.foodrecipe.Constants.Companion.MEAL_THUMB
import com.myapps.foodrecipe.activities.MainActivity
import com.myapps.foodrecipe.activities.MealActivity
import com.myapps.foodrecipe.databinding.FragmentMealBottomSheetBinding
import com.myapps.foodrecipe.viewModel.HomeViewModel


private const val MEAL_Id="mealId"


class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private  var _binding:FragmentMealBottomSheetBinding?=null
    private  val binding get() = _binding!!
    private var mealId: String? = null
    private lateinit var viewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_Id)
            viewModel=(activity as MainActivity).viewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMealBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       mealId?.let {
           viewModel.getMealById(it)
       }
        observeBottomSheetMeal()


        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheetLayout.setOnClickListener {
            if(mealName!=null && mealThumb!=null ){
                val intent=Intent(activity,MealActivity::class.java)
                intent.putExtra(MEAL_NAME,mealName)
                intent.putExtra(MEAL_THUMB,mealThumb)
                intent.putExtra(MEAL_ID,mealId)
                startActivity(intent)

            }
        }

    }

    private var mealName:String?=null
    private var mealThumb:String?=null
    private fun observeBottomSheetMeal(){
        viewModel.observerBottomSheetMeal().observe(viewLifecycleOwner) { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.bottomSheetImgMeal)
            binding.tvBottomSheetArea.text = meal.strArea
            binding.tvBottomSheetCategory.text = meal.strCategory
            binding.tvMealName.text = meal.strMeal
            binding.tvReadMore
            mealName = meal.strMeal
            mealThumb = meal.strMealThumb

        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_Id, param1)
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}