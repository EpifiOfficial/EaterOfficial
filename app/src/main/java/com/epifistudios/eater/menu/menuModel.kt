package com.epifistudios.eater.Menu

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class menuModel (
    val IvProduct:String = "",
    val ProductDesc:String = "",
    val ProductName:String = "",
    val ProductPrice:String =""
    /*val DessertIv1:String = "",
    val DessertIv2:String = "",
    val DessertIv3:String = "",
    val DrinksIv1:String = "",
    val DrinksIv2:String = "",
    val DrinksIv3:String = ""*/

): Parcelable