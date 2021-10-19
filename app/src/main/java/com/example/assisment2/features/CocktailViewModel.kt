package com.example.assisment2.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.assisment2.room.Drinks
import com.example.assisment2.room.Favourite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val repository: CocktailRepository
) : ViewModel() {
    fun findCocktails(name : String)=
    repository.getCocktails(name).asLiveData()


    suspend fun insertFavourite(favourite: Favourite) = repository.insertFavourite(favourite)

    fun getFavourites() = repository.getFavourites().asLiveData()

    suspend fun deleteFavourite(favourite: Favourite) = repository.deleteFavourite(favourite)

}