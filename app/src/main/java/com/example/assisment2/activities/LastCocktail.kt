package com.example.assisment2.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assisment2.R
import com.example.assisment2.adapters.CocktailAdapter
import com.example.assisment2.databinding.ActivityMainBinding
import com.example.assisment2.databinding.ActivitySeachingBinding
import com.example.assisment2.features.CocktailViewModel
import com.example.assisment2.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LastCocktail : AppCompatActivity() {

    private val viewModel: CocktailViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cocktailAdapter = CocktailAdapter()
        binding.apply {
            recycle.apply {
                adapter = cocktailAdapter
                layoutManager = LinearLayoutManager(this@LastCocktail)
            }
            viewModel.findCocktails("rum").observe(this@LastCocktail) {
                cocktailAdapter.setAdapter(it.data!!)
                progress.isVisible = (it is Resource.Loading && it.data.isNullOrEmpty())
                error.isVisible = it is Resource.Error
                val error = it.error.toString()
                this.error.text = error
            }
            setSupportActionBar(toolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.toolbar.inflateMenu(R.menu.main_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_search -> {
                val intent = Intent(this@LastCocktail,SearchCocktail::class.java)
                startActivity(intent)
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }
    }
}