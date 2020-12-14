package com.epifistudios.eater.Menu

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class dessertMenuModel (
    val IvProduct:String = "",
    val ProductDesc:String = "",
    val ProductName:String = "",
    val ProductPrice:String = ""
   /* val DrinksIv1:String = "",
    val DrinksIv2:String = "",
    val DrinksIv3:String = ""*/

): Parcelable