package com.example.assisment2.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    repository: CocktailRepository
) : ViewModel() {

    val cocktails = repository.getCocktails().asLiveData()
//    private val cocktailLiveData = MutableLiveData<Json4Kotlin_Base>()
//    val cocktails: LiveData<Json4Kotlin_Base> = cocktailLiveData
//
//    init {
//        viewModelScope.launch {
//            val cocktails = api.getSearchedCocktail("rum")
//            cocktailLiveData.value = cocktails
//        }
//    }
}