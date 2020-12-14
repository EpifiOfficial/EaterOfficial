package com.epifistudios.eater.Steps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epifistudios.eater.R
import com.epifistudios.eater.Search.SearchListAdapter
import com.epifistudios.eater.Search.SearchModel
import kotlinx.android.synthetic.main.steps_cell.view.*

class StepsListAdapter (var stepsList:List<StepsModel>, val clickListener: (StepsModel) -> Unit):RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder>(){
    class StepsListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(stepsModel: StepsModel, clickListener: (StepsModel) -> Unit) {
            itemView.TvStep1.text = "Steps 1: "+stepsModel.steps1
            itemView.tvStep2.text = "Steps 2: "+stepsModel.steps2
            itemView.tvStep3.text = "Steps 3: "+stepsModel.steps3
            itemView.tvStep4.text = "Steps 4: "+stepsModel.steps4
            itemView.tvStep5.text = "Steps 5: "+stepsModel.steps5

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.steps_cell,parent,false)
        return StepsListAdapter.StepsListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }

    override fun onBindViewHolder(holder: StepsListViewHolder, position: Int) {
        holder.bind(stepsList[position],clickListener)
    }




}