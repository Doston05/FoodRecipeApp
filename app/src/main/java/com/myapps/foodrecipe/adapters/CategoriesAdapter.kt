package com.myapps.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.foodrecipe.databinding.CategoryItemBinding
import com.myapps.foodrecipe.pojo.Category

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private var categoryList=ArrayList<Category>()
    var onItemClick:((Category)->Unit)? = null

    fun setCategoryList(categoryList:List<Category>){
        this.categoryList=categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
 return ViewHolder(binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
    class ViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)
}