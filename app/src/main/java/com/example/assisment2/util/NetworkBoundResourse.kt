package com.example.assisment2.util

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.assisment2.room.Drinks
import com.example.assisment2.room.Favourite
import kotlinx.coroutines.flow.*


const val LAST_SEARCHED_KEY = "ites is"
const val DEFAULT_LAST_SEARCHED = "cocktail"

/**
 * @this function is responsible for fetching and saving data
 * in local database
 * It will decide to save data in database
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}

/**
 * @param drinks this converts
 * @drinka object to
 * @favourite object
 * @return Favourite object
 */
fun drinkToFavourite(drinks: Drinks): Favourite {
    return Favourite(
        drinks.idDrink,
        drinks.strDrink,
        drinks.strDrinkAlternate,
        drinks.strTags,
        drinks.strCategory,
        drinks.strAlcoholic,
        drinks.strGlass,
        drinks.strInstructions,
        drinks.strDrinkThumb,
        drinks.strIngredient1,
        drinks.strIngredient2,
        drinks.strIngredient3,
        drinks.strIngredient4,
        drinks.strIngredient5,
        drinks.strIngredient6,
        drinks.strMeasure1,
        drinks.strMeasure2,
        drinks.strMeasure3,
        drinks.strMeasure4,
        drinks.strMeasure5,
        drinks.strMeasure6,
        drinks.strImageSource,
        drinks.strImageAttribution
    )
}

/**
 * this function is responsible for setting last search cocktail in sharedPreference
 * @param context and
 * @param search to be saved
 */
fun setSearch(context: Context, search: String) {
    val sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putString(LAST_SEARCHED_KEY, search)
    editor.apply()
}

/**
 * @return String or Last searched item
 * by using sharedPreference
 */
fun getSearch(context: Context): String? {
    val sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(context)
    return sharedPreferences.getString(LAST_SEARCHED_KEY, DEFAULT_LAST_SEARCHED)
}

/**
 * @param list list of ingredients and their measurement
 * this function separate  ingredients and their measurement and
 * @return Pair of ingredients list and their measurement list
 */
fun getIngredientAndMeasure(list: List<String?>): Pair<List<String>, List<String>> {
    val ingredient: MutableList<String> = ArrayList()
    val measure: MutableList<String> = ArrayList()
    var i = 0
    while (i < 6 && list[i] != null) {
        ingredient.add(list[i]!!)
        if (list[i + 6] != null)
            measure.add(list[i + 6]!!)
        i++
    }
    return Pair(ingredient.toList(), measure.toList())
}
