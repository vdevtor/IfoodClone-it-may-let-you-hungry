package com.example.ifoodclone.helper

import com.example.ifoodclone.models.FoodModel

interface OnClickContract {
    fun onClick(item:FoodModel)
}