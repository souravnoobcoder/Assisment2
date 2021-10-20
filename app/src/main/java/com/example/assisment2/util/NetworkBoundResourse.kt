package com.example.assisment2.util

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.assisment2.room.Drinks
import com.example.assisment2.room.Favourite
import kotlinx.coroutines.flow.*


const val LAST_SEARCHED_KEY = "ites is"
const val DEFAULT_LAST_SEARCHED = "cocktail"

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

fun setSearch(context: Context, search: String) {
    val sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putString(LAST_SEARCHED_KEY, search)
    editor.apply()
}

fun getSearch(context: Context): String? {
    val sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(context)
    return sharedPreferences.getString(LAST_SEARCHED_KEY, DEFAULT_LAST_SEARCHED)
}

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
