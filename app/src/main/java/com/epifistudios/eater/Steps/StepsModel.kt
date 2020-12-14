package com.epifistudios.eater.Steps

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StepsModel(
    val steps1:String = "",
    val steps2:String = "",
    val steps3:String = "",
    val steps4:String = "",
    val steps5:String = ""

    ): Parcelable