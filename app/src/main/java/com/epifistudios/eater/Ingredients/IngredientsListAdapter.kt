package com.epifistudios.eater.Ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epifistudios.eater.R
import kotlinx.android.synthetic.main.cell_ingredient.view.*

class IngredientsListAdapter (var ingredientList:List<IngredientsModel>, val clickListener:(IngredientsModel)-> Unit):
    RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder>(){
    class IngredientsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(ingredientsModel: IngredientsModel, clickListener: (IngredientsModel) -> Unit){
            itemView.TvNameIngredient1.text = ingredientsModel.ingredientes1
            itemView.TvNameIngredient2.text = ingredientsModel.ingredientes2
            itemView.TvNameIngredient3.text = ingredientsModel.ingredientes3
            itemView.TvNameIngredient4.text = ingredientsModel.ingredientes4

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_ingredient,parent,false)
        return IngredientsViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(ingredientList[position],clickListener)
    }

}