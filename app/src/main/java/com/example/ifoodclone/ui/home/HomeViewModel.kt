package com.example.ifoodclone.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifoodclone.base.BaseHomeRepository
import com.example.ifoodclone.data.HomeRepository
import com.example.ifoodclone.models.CategoryModel
import com.example.ifoodclone.models.FoodModel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.get

class HomeViewModel(val repository : BaseHomeRepository) : ViewModel(), KoinComponent {

    var popularFoodOnResult = MutableLiveData<ArrayList<FoodModel>>()
    var categoriesOnResult = MutableLiveData<ArrayList<CategoryModel>>()


    fun getCategories() {

        viewModelScope.launch {
            if (repository.populateCategory().isNotEmpty()) {
                categoriesOnResult.postValue(repository.populateCategory())
            }
        }
    }

    fun getPopularFoods() {

        viewModelScope.launch {
            if (repository.populatePopularList().isNotEmpty()) {
                popularFoodOnResult.postValue(repository.populatePopularList())
            }
        }
    }
}