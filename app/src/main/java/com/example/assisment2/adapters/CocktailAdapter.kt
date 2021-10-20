package com.example.assisment2.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.assisment2.databinding.DrinksItemBinding
import com.example.assisment2.room.Drinks
import com.example.assisment2.util.getIngredientAndMeasure
import com.squareup.picasso.Picasso

class CocktailAdapter : RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {
    private var expanded = false
    private var listener: OnItemClickListener? = null
    private var cocktails: List<Drinks> = emptyList()

    fun setAdapter(cocktails: List<Drinks>) {
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

            /**
             * when we click on drinks name then we save it to
             * local database to our favourites list
             */
            favourite.setOnClickListener {
                val adapterPosition = holder.adapterPosition
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener!!.onItemClicked(cocktails[adapterPosition], adapterPosition)
                    rate.setColorFilter(Color.CYAN)
                }
            }

            /**
             * it will show ingredients visible when clicked and ingredients are not visible
             * and it will make ingredients invisible when clicked and ingredients are visible
             *
             * it instantiate ingredients adapter
             * by passing list of ingredients
             */
            expandItem.setOnClickListener {
                if (expanded) {
                    TransitionManager.beginDelayedTransition(more, AutoTransition())
                    viewMore.visibility = VISIBLE
                    more.visibility = GONE
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
                    viewMore.visibility = GONE
                    more.visibility = VISIBLE
                    expanded = true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    class ViewHolder(var itemBinding: DrinksItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClicked(drink: Drinks?, position: Int)
    }
}