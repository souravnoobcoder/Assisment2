package com.example.assisment2.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCocktails(drinks: List<Drinks>)

    @Query("SELECT * FROM lastSearched")
    fun getLastCocktails(): Flow<List<Drinks>>

    @Query("DELETE FROM lastSearched")
    fun deleteLastCocktails()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Query("SELECT * FROM favourite")
    fun getFavourites(): Flow<List<Favourite>>

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)
}