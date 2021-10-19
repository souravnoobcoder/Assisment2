package com.example.assisment2.util

import com.example.assisment2.room.Drinks
import com.example.assisment2.room.Favourite
import kotlinx.coroutines.flow.*

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