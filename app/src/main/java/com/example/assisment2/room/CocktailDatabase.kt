package com.example.assisment2.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Drinks::class,Favourite::class],version = 1,exportSchema = false)
abstract class CocktailDatabase : RoomDatabase(){
    abstract fun getCocktailDao() : CocktailDao
}