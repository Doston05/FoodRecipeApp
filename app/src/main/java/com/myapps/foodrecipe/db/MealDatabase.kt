package com.myapps.foodrecipe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myapps.foodrecipe.pojo.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
 abstract class MealDatabase :RoomDatabase(){
     abstract fun mealDao():MealDao

     companion object {
          private var INSTANCE: MealDatabase? = null

         fun getInstance(context: Context): MealDatabase {
             if (INSTANCE == null) {
                 INSTANCE = Room.databaseBuilder(
                     context,
                     MealDatabase::class.java,
                     "meal.db"
                 ).fallbackToDestructiveMigration()
                     .build()
             }
             return INSTANCE as MealDatabase
         }
     }
}