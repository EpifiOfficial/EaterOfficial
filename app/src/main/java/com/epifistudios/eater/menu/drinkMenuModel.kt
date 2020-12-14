package com.epifistudios.eater.Menu

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class drinkMenuModel(
    val IvProduct:String = "",
    val ProductDesc:String = "",
    val ProductName:String = "",
    val ProductPrice:String=""
): Parcelable