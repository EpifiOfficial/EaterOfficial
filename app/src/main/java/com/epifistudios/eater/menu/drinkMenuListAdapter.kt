package com.epifistudios.eater.Menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epifistudios.eater.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_product.view.*

class drinkMenuListAdapter (var drinksMenuList:List<drinkMenuModel>,val clickListener: (drinkMenuModel) -> Unit): RecyclerView.Adapter<drinkMenuListAdapter.DrinksMenuListViewHolder>() {
    class DrinksMenuListViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(drinksMenuModel: drinkMenuModel, clickListener: (drinkMenuModel) -> Unit){
            if (drinksMenuModel.IvProduct=="null"){
                itemView.IvProduct.visibility = View.GONE;
            }else{
                Picasso.get()
                    .load(drinksMenuModel.IvProduct)
                    .fit()
                    .into(itemView.IvProduct)
            }
            if (drinksMenuModel.ProductDesc=="null"){
                itemView.TvProductDesc.visibility = View.GONE;

            }else{
                itemView.TvProductDesc.text = drinksMenuModel.ProductDesc

            }
            if (drinksMenuModel.ProductName=="null"){
                itemView.TvProductName.visibility = View.GONE;

            }else{
                itemView.TvProductName.text = drinksMenuModel.ProductName
            }
            if (drinksMenuModel.ProductPrice=="null"){
                itemView.TvProductPrice.visibility = View.GONE;

            }else{
                itemView.TvProductPrice.text = drinksMenuModel.ProductPrice+"€"
            }
            itemView.setOnClickListener{
                clickListener(drinksMenuModel)
                itemView.CvPortraitMode.visibility = View.VISIBLE
                if (drinksMenuModel.IvProduct=="null"){
                    itemView.IvProductPM.visibility = View.GONE;
                }else{
                    Picasso.get()
                        .load(drinksMenuModel.IvProduct)
                        .fit()
                        .centerCrop()
                        .into(itemView.IvProductPM)
                }
                if (drinksMenuModel.ProductDesc=="null"){
                    itemView.TvProductDescPM.visibility = View.GONE;

                }else{
                    itemView.TvProductDescPM.text = drinksMenuModel.ProductDesc

                }
                if (drinksMenuModel.ProductName=="null"){
                    itemView.TvProductNamePM.visibility = View.GONE;

                }else{
                    itemView.TvProductNamePM.text = drinksMenuModel.ProductName
                }
                if (drinksMenuModel.ProductPrice=="null"){
                    itemView.TvProductPricePM.visibility = View.GONE;

                }else{
                    itemView.TvProductPricePM.text = drinksMenuModel.ProductPrice+"€"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksMenuListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_product,parent,false)
        return DrinksMenuListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return drinksMenuList.size
    }

    override fun onBindViewHolder(holder: DrinksMenuListViewHolder, position: Int) {
        holder.bind(drinksMenuList[position],clickListener)
    }
}