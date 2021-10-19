package com.example.assisment2.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assisment2.adapters.CocktailAdapter
import com.example.assisment2.adapters.FavouriteCocktailAdapter
import com.example.assisment2.databinding.ActivityFavouriteCocktailsBinding
import com.example.assisment2.features.CocktailViewModel
import com.example.assisment2.room.Drinks
import com.example.assisment2.room.Favourite
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteCocktails : AppCompatActivity() {

    private val viewModel: CocktailViewModel by viewModels()
    lateinit var binding: ActivityFavouriteCocktailsBinding
    private var favourites: List<Favourite>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteCocktailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cocktailAdapter = FavouriteCocktailAdapter()
        binding.apply {
            favouriteRecyclerView.apply {
                adapter = cocktailAdapter
                layoutManager = LinearLayoutManager(this@FavouriteCocktails)
            }
            viewModel.getFavourites().observe(this@FavouriteCocktails){
                favourites = it
                cocktailAdapter.setAdapter(it)
            }
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val favourite=favourites?.get(viewHolder.adapterPosition)!!
                CoroutineScope(IO).launch {
                    viewModel.deleteFavourite(favourite)
                }
                Toast.makeText(this@FavouriteCocktails, "${favourite.strDrink} is " +
                        "\n deleted from favourite", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.favouriteRecyclerView)
    }
}