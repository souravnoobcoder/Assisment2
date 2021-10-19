package com.example.assisment2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assisment2.databinding.DrinksItemBinding
import com.example.assisment2.room.Favourite
import com.squareup.picasso.Picasso

class FavouriteCocktailAdapter : RecyclerView.Adapter<FavouriteCocktailAdapter.ViewHolder>() {
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
        }
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    class ViewHolder(var itemBinding: DrinksItemBinding) : RecyclerView.ViewHolder(itemBinding.root)


}