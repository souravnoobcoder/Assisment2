package com.example.assisment2.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisment2.api.CocktailApi
import com.example.assisment2.dataClasses.Drinks
import com.example.assisment2.dataClasses.Json4Kotlin_Base
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    api: CocktailApi
) : ViewModel() {

    private val cocktailLiveData = MutableLiveData<Json4Kotlin_Base>()
    val cocktails: LiveData<Json4Kotlin_Base> = cocktailLiveData

    init {
        viewModelScope.launch {
            val cocktails = api.getSearchedCocktail("rum")
            cocktailLiveData.value = cocktails
        }
    }
}