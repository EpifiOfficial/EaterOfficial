package com.epifistudios.eater.Menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.epifistudios.eater.R
import com.epifistudios.eater.Search.SearchModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_product.view.*
import kotlinx.android.synthetic.main.cell_receta.view.*


class MenuListAdapter(var menuList:List<menuModel>, val clickListener: (menuModel) -> Unit):RecyclerView.Adapter<MenuListAdapter.MenuListViewHolder>() {
    class MenuListViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(menuModel: menuModel, clickListener: (menuModel) -> Unit){
            if (menuModel.IvProduct=="null"){
                itemView.IvProduct.visibility = View.GONE;
            }else{
                Picasso.get()
                    .load(menuModel.IvProduct)
                    .fit()
                    .into(itemView.IvProduct)
            }
           if (menuModel.ProductDesc=="null"){
               itemView.TvProductDesc.visibility = View.GONE;

           }else{
               itemView.TvProductDesc.text = menuModel.ProductDesc

           }
            if (menuModel.ProductName=="null"){
                itemView.TvProductName.visibility = View.GONE;

            }else{
               itemView.TvProductName.text = menuModel.ProductName
            }
            if (menuModel.ProductPrice=="null"){
                itemView.TvProductPrice.visibility = View.GONE;

            }else{
                itemView.TvProductPrice.text = menuModel.ProductPrice+"€"
            }
            itemView.setOnClickListener{
                clickListener(menuModel)
                itemView.CvPortraitMode.visibility = View.VISIBLE
                if (menuModel.IvProduct=="null"){
                    itemView.IvProductPM.visibility = View.GONE;
                }else{
                    Picasso.get()
                        .load(menuModel.IvProduct)
                        .fit()
                        .into(itemView.IvProductPM)
                }
                if (menuModel.ProductDesc=="null"){
                    itemView.TvProductDescPM.visibility = View.GONE;

                }else{
                    itemView.TvProductDescPM.text = menuModel.ProductDesc

                }
                if (menuModel.ProductName=="null"){
                    itemView.TvProductNamePM.visibility = View.GONE;

                }else{
                    itemView.TvProductNamePM.text = menuModel.ProductName
                }
                if (menuModel.ProductPrice=="null"){
                    itemView.TvProductPricePM.visibility = View.GONE;

                }else{
                    itemView.TvProductPricePM.text = menuModel.ProductPrice+"€"
                }
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_product,parent,false)
        return MenuListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuListViewHolder, position: Int) {
        holder.bind(menuList[position],clickListener)
    }

}