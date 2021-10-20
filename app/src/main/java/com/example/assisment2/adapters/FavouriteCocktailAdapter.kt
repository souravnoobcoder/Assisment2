package com.example.assisment2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.assisment2.databinding.DrinksItemBinding
import com.example.assisment2.room.Favourite
import com.example.assisment2.util.getIngredientAndMeasure
import com.squareup.picasso.Picasso

class FavouriteCocktailAdapter : RecyclerView.Adapter<FavouriteCocktailAdapter.ViewHolder>() {

    private var expanded = false
    private var cocktails: List<Favourite> = emptyList()
    fun setAdapter(cocktails: List<Favourite>) {
        this.cocktails = cocktails
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DrinksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cocktail = cocktails[position]
        holder.itemBinding.apply {
            Picasso.get().load(cocktail.strDrinkThumb).noFade().fit().into(cocktailThumbnail)
            cocktailName.text = cocktail.strDrink
            categoryAlcoholic.text = "${cocktail.strCategory} and ${cocktail.strAlcoholic}"
            instructionContent.text = cocktail.strInstructions
            expandItem.setOnClickListener {
                if (expanded) {
                    TransitionManager.beginDelayedTransition(more, AutoTransition())
                    viewMore.visibility = View.VISIBLE
                    more.visibility = View.GONE
                    expanded = false
                } else {
                    val ingredients = listOf(
                        cocktail.strIngredient1,
                        cocktail.strIngredient2,
                        cocktail.strIngredient3,
                        cocktail.strIngredient4,
                        cocktail.strIngredient5,
                        cocktail.strIngredient6,
                        cocktail.strMeasure1,
                        cocktail.strMeasure2,
                        cocktail.strMeasure3,
                        cocktail.strMeasure4,
                        cocktail.strMeasure5,
                        cocktail.strMeasure6,
                    )
                    val adapterContent = getIngredientAndMeasure(ingredients)
                    val ingredientsAdapter =
                        IngredientsAdapter(adapterContent.first, adapterContent.second)
                    ingredientRecycle.apply {
                        adapter = ingredientsAdapter
                        layoutManager = LinearLayoutManager(context)
                    }
                    TransitionManager.beginDelayedTransition(more, AutoTransition())
                    viewMore.visibility = View.GONE
                    more.visibility = View.VISIBLE
                    expanded = true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    class ViewHolder(var itemBinding: DrinksItemBinding) : RecyclerView.ViewHolder(itemBinding.root)


}