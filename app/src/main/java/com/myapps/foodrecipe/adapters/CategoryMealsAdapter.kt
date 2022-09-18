package com.myapps.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.foodrecipe.databinding.MealItemsBinding
import com.myapps.foodrecipe.pojo.MealsByCategory

class CategoryMealsAdapter():RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {
    var mealsList=ArrayList<MealsByCategory>()
    var onItemClick:((MealsByCategory)->Unit)? = null
    fun setMealsList(mealsList:List<MealsByCategory>){
        this.mealsList=mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: MealItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
return ViewHolder(MealItemsBinding.inflate(LayoutInflater.from(parent.context)))   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
    holder.binding.tvMealName.text=mealsList[position].strMeal
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
            return mealsList.size   }
}