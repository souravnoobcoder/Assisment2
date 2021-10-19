package com.example.assisment2.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    val repository: CocktailRepository
) : ViewModel() {
    fun findCocktails(name : String)=
    repository.getCocktails(name).asLiveData()
}