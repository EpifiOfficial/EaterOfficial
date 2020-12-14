package com.epifistudios.eater.Menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epifistudios.eater.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_product.view.*

class dessertMenuListAdapter (var dessertMenuList:List<dessertMenuModel>, val clickListener: (dessertMenuModel) -> Unit): RecyclerView.Adapter<dessertMenuListAdapter.DessertMenuListViewHolder>() {
    class DessertMenuListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(dessertMenuModel: dessertMenuModel, clickListener: (dessertMenuModel) -> Unit){
            if (dessertMenuModel.IvProduct=="null"){
                itemView.IvProduct.visibility = View.GONE;
            }else{
                Picasso.get()
                    .load(dessertMenuModel.IvProduct)
                    .fit()
                    .into(itemView.IvProduct)
            }
            if (dessertMenuModel.ProductDesc=="null"){
                itemView.TvProductDesc.visibility = View.GONE;

            }else{
                itemView.TvProductDesc.text = dessertMenuModel.ProductDesc

            }
            if (dessertMenuModel.ProductName=="null"){
                itemView.TvProductName.visibility = View.GONE;

            }else{
                itemView.TvProductName.text = dessertMenuModel.ProductName
            }
            if (dessertMenuModel.ProductPrice=="null"){
                itemView.TvProductPrice.visibility = View.GONE;

            }else{
                itemView.TvProductPrice.text = dessertMenuModel.ProductPrice+"€"
            }
            itemView.setOnClickListener{
                clickListener(dessertMenuModel)
                itemView.CvPortraitMode.visibility = View.VISIBLE
                if (dessertMenuModel.IvProduct=="null"){
                    itemView.IvProductPM.visibility = View.GONE;
                }else{
                    Picasso.get()
                        .load(dessertMenuModel.IvProduct)
                        .fit()
                        .into(itemView.IvProductPM)
                }
                if (dessertMenuModel.ProductDesc=="null"){
                    itemView.TvProductDescPM.visibility = View.GONE;

                }else{
                    itemView.TvProductDescPM.text = dessertMenuModel.ProductDesc

                }
                if (dessertMenuModel.ProductName=="null"){
                    itemView.TvProductNamePM.visibility = View.GONE;

                }else{
                    itemView.TvProductNamePM.text = dessertMenuModel.ProductName
                }
                if (dessertMenuModel.ProductPrice=="null"){
                    itemView.TvProductPricePM.visibility = View.GONE;

                }else{
                    itemView.TvProductPricePM.text = dessertMenuModel.ProductPrice+"€"
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DessertMenuListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_product,parent,false)
        return DessertMenuListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return dessertMenuList.size
    }

    override fun onBindViewHolder(holder: DessertMenuListViewHolder, position: Int) {
        holder.bind(dessertMenuList[position],clickListener)
    }


}