package com.example.assisment2.activities

import android.content.Intent
import android.os.Bundle
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
import com.example.assisment2.util.getSearch
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


        val lastCocktail = getSearch(this)
        val cocktailAdapter = CocktailAdapter()
        binding.apply {
            recycle.apply {
                adapter = cocktailAdapter
                layoutManager = LinearLayoutManager(this@LastCocktail)
            }
            viewModel.findCocktails(lastCocktail!!).observe(this@LastCocktail) {
                cocktailAdapter.setAdapter(it.data!!)
                progress.isVisible = (it is Resource.Loading && it.data.isNullOrEmpty())
                it.error?.printStackTrace()
            }
            setSupportActionBar(toolbar)
        }

        /**
         * it will fetch the data from local database but
         * first fetch it from server with searched drink and
         * then save it to local database
         */
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

    /**
     * it handles menu clicks
     */
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
}