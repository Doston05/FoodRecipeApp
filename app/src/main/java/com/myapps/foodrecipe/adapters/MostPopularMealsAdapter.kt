package com.myapps.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.foodrecipe.databinding.PopularMealRvBinding
import com.myapps.foodrecipe.pojo.MealsByCategory

class MostPopularMealsAdapter :RecyclerView.Adapter<MostPopularMealsAdapter.ViewHolder>() {

    private var mealList=ArrayList<MealsByCategory>()
    lateinit var onItemClick:(MealsByCategory)->Unit
     var onLongItemClick:((MealsByCategory)->Unit)? = null

    fun setMeals(mealList: ArrayList<MealsByCategory>){
        this.mealList=mealList
        notifyDataSetChanged()
    }

    class ViewHolder( var binding: PopularMealRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
return ViewHolder(PopularMealRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position]
                .strMealThumb).into(holder
                .binding.imgPopularMealItem)

    holder.itemView.setOnClickListener {
        onItemClick.invoke(mealList[position])
    }
        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealList[position])

            true
        }
    }

    override fun getItemCount(): Int {
       return mealList.size
    }
}