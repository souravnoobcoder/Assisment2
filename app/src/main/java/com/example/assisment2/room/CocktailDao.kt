package com.example.assisment2.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    /**
     * @insert list of drinks to local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCocktails(drinks: List<Drinks>)

    /**
     * @return flow of list of Drinks
     */
    @Query("SELECT * FROM lastSearched")
    fun getLastCocktails(): Flow<List<Drinks>>

    /**
     * it will delete all item in database
     */
    @Query("DELETE FROM lastSearched")
    fun deleteLastCocktails()

    /**
     * @insert favourite drink to favourite table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    /**
     * @return flow of list of Favourites
     */
    @Query("SELECT * FROM favourite")
    fun getFavourites(): Flow<List<Favourite>>

    /**
     * it will delete certain item in database
     */
    @Delete
    suspend fun deleteFavourite(favourite: Favourite)
}