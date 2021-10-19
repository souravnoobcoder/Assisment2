package com.example.assisment2.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assisment2.adapters.CocktailAdapter
import com.example.assisment2.databinding.ActivityMainBinding
import com.example.assisment2.features.CocktailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CocktailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cocktailAdapter=CocktailAdapter()
        binding.apply {
            recycle.apply {
                adapter=cocktailAdapter
                layoutManager=LinearLayoutManager(this@MainActivity)
            }
            viewModel.cocktails.observe(this@MainActivity){
                cocktails -> cocktailAdapter.setAdapter(cocktails.drinks)
            }
        }

    }
}