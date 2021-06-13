package com.example.ifoodclone.base

import com.example.ifoodclone.models.CategoryModel
import com.example.ifoodclone.models.FoodModel

interface BaseHomeRepository {
    suspend fun populateCategory() : ArrayList<CategoryModel>
    suspend fun populatePopularList() : ArrayList<FoodModel>
}