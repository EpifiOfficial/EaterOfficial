package com.epifistudios.eater.Ingredients

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IngredientsModel(
    val ingredientes1 :String ="",
    val ingredientes2 :String="",
    val ingredientes3 :String="",
    val ingredientes4 :String=""


    ): Parcelable