package com.example.assisment2.features

import androidx.room.withTransaction
import com.example.assisment2.api.CocktailApi
import com.example.assisment2.room.CocktailDatabase
import com.example.assisment2.room.Drinks
import com.example.assisment2.room.Favourite
import com.example.assisment2.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val api: CocktailApi,
    private val database: CocktailDatabase
) {
    private val cocktailDao = database.getCocktailDao()

    fun getCocktails(name : String) = networkBoundResource(
        query = {
            cocktailDao.getLastCocktails()
        },
        fetch = {
            api.getSearchedCocktail(name)
        },
        saveFetchResult = { cocktails ->
            database.withTransaction {
                cocktailDao.deleteLastCocktails()
                cocktailDao.insertNewCocktails(cocktails.drinks)
            }
        }
    )

    suspend fun insertFavourite(favourite: Favourite) =cocktailDao.insertFavourite(favourite)



    fun getFavourites() = cocktailDao.getFavourites()


    suspend fun deleteFavourite(favourite: Favourite) = cocktailDao.deleteFavourite(favourite)

}