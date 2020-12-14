package com.epifistudios.eater.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epifistudios.eater.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_receta.view.*

class SearchListAdapter (var searchList:List<SearchModel>, val clickListener: (SearchModel) -> Unit):RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>(){
    class SearchListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(searchModel: SearchModel, clickListener: (SearchModel) -> Unit){
            itemView.TvRecipeName.text = searchModel.name_recipe
            itemView.TvNumeroIndredientes.text = searchModel.tiempo_Preparacion+ " min"
            itemView.TvNumeroRestaurantes.text = searchModel.numeroRestaurantesDisponibles.toString()+" restaurantes disponibles"
            Picasso.get()
                .load(searchModel.imageUrl)
                .fit()
                .into(itemView.IvRecipe)
            itemView.setOnClickListener{
                clickListener(searchModel)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_receta,parent,false)
        return SearchListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(searchList[position],clickListener)
    }



}
