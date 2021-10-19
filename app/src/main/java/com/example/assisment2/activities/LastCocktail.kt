package com.example.assisment2.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assisment2.R
import com.example.assisment2.adapters.CocktailAdapter
import com.example.assisment2.databinding.ActivityMainBinding
import com.example.assisment2.features.CocktailViewModel
import com.example.assisment2.room.Drinks
import com.example.assisment2.util.Resource
import com.example.assisment2.util.drinkToFavourite
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LastCocktail : AppCompatActivity() {

    private val viewModel: CocktailViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val lastCocktail = themeStatus
        val cocktailAdapter = CocktailAdapter()
        binding.apply {
            recycle.apply {
                adapter = cocktailAdapter
                layoutManager = LinearLayoutManager(this@LastCocktail)
            }
            viewModel.findCocktails(lastCocktail!!).observe(this@LastCocktail) {
                cocktailAdapter.setAdapter(it.data!!)
                progress.isVisible = (it is Resource.Loading && it.data.isNullOrEmpty())
                error.isVisible = it is Resource.Error
                val error = it.error.toString()
                this.error.text = error
            }
            setSupportActionBar(toolbar)
        }
        cocktailAdapter.setOnItemClickListener(object : CocktailAdapter.OnItemClickListener {
            override fun onItemClicked(drink: Drinks?, position: Int) {
                CoroutineScope(IO).launch {
                    viewModel.insertFavourite(drinkToFavourite(drink!!))
                }
                Toast.makeText(
                    this@LastCocktail, "${drink!!.strDrink} is " +
                            "\n Added to Favourites", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.toolbar.inflateMenu(R.menu.main_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                val intent = Intent(this@LastCocktail, SearchCocktail::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_favourite -> {
                val intent = Intent(this@LastCocktail, FavouriteCocktails::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    var themeStatus: String?
        get() {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext)
            return sharedPreferences.getString(LAST_SEARCHED_KEY, DEFAULT_LAST_SEARCHED)
        }
        set(mode) {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = sharedPreferences.edit()
            editor.putString(DEFAULT_LAST_SEARCHED, mode)
            editor.apply()
            finish()
            startActivity(intent)
        }

    companion object {
        const val LAST_SEARCHED_KEY = "ites is"
        const val DEFAULT_LAST_SEARCHED = "cocktail"
    }
}