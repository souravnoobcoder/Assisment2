package com.example.assisment2.activities

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assisment2.adapters.CocktailAdapter
import com.example.assisment2.databinding.ActivitySeachingBinding
import com.example.assisment2.features.CocktailViewModel
import com.example.assisment2.room.Drinks
import com.example.assisment2.room.Favourite
import com.example.assisment2.util.drinkToFavourite
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchCocktail : AppCompatActivity() {

    private val viewModel: CocktailViewModel by viewModels()
    lateinit var binding: ActivitySeachingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeachingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cocktailAdapter = CocktailAdapter()
        binding.apply {
            searchRecycle.apply {
                adapter = cocktailAdapter
                layoutManager = LinearLayoutManager(this@SearchCocktail)
            }
            searchCocktails.setOnEditorActionListener { v, _, _ ->
                searchProgress.isVisible=true
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
                viewModel.findCocktails(v?.text.toString()).observe(this@SearchCocktail) {
                    if (it.data!!.isNotEmpty()){
                        cocktailAdapter.setAdapter(it.data)
                        searchProgress.isVisible=false
                        TODO()
                    }

                }
                true
            }
        }
        cocktailAdapter.setOnItemClickListener(object : CocktailAdapter.OnItemClickListener {
            override fun onItemClicked(drink: Drinks?, position: Int) {
                CoroutineScope(IO).launch {
                    viewModel.insertFavourite( drinkToFavourite(drink!!))
                }
                Toast.makeText(this@SearchCocktail, "${drink!!.strDrink} is " +
                        "\n Added to Favourites", Toast.LENGTH_SHORT).show()
            }
        })
    }
}