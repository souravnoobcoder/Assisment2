package com.example.assisment2.activities

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assisment2.adapters.CocktailAdapter
import com.example.assisment2.databinding.ActivitySeachingBinding
import com.example.assisment2.features.CocktailViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
                viewModel.findCocktails(v?.text.toString()).observe(this@SearchCocktail) {
                    searchProgress.isVisible=true
                    cocktailAdapter.setAdapter(it.data!!)
                    searchProgress.isVisible=false
                }
                true
            }
        }
    }
}