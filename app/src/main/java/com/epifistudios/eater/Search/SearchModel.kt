package com.epifistudios.eater.Search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchModel(
    val name_recipe:String = "",
    val tiempo_Preparacion:String="",
    val numeroRestaurantesDisponibles: Int? =null,
    val imageUrl:String = "",
    val numero_Receta: String = "",
    val equipments_1:String = "",
    val equipments_2:String = ""
): Parcelable