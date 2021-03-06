package com.example.assisment2.api

import com.example.assisment2.room.Json4Kotlin_Base
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    /**
     * @fetches data from api throw
     * @param cocktail
     */
    @GET("search.php?")
    suspend fun getSearchedCocktail(@Query("s") cocktail: String): Json4Kotlin_Base

    companion object {
        const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    }
}