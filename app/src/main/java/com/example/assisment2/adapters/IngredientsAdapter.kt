package com.example.assisment2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assisment2.databinding.IngrediantsItemBinding

class IngredientsAdapter(private val ingredient: List<String>, private val measure: List<String>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            IngrediantsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.apply {
            ingredientItem.text = ingredient[position]
            ingredientMeasure.text = measure[position]
        }
    }

    override fun getItemCount(): Int {
        return measure.size
    }

    class ViewHolder(val itemBinding: IngrediantsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}