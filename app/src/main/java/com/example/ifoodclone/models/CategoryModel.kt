package com.example.ifoodclone.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(

        val title: String?,
        val pic: String?

) : Parcelable {
}