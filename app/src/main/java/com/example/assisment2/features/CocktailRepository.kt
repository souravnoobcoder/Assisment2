package com.example.assisment2.features

import androidx.room.withTransaction
import com.example.assisment2.api.CocktailApi
import com.example.assisment2.room.CocktailDatabase
import com.example.assisment2.util.networkBoundResource
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
}