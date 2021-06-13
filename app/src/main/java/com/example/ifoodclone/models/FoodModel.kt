package com.example.ifoodclone.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodModel(

        var title: String?,
        var pic: String?,
        var description: String?,
        var fee: Double?,
        var numberInCart: Int?

) : Parcelable
