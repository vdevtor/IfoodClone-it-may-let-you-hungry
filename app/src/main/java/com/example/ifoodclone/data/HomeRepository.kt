package com.example.ifoodclone.data

import com.example.ifoodclone.base.BaseHomeRepository
import com.example.ifoodclone.models.CategoryModel
import com.example.ifoodclone.models.FoodModel

class HomeRepository : BaseHomeRepository {

    override suspend fun populateCategory(): ArrayList<CategoryModel> {

        val categoryList = ArrayList<CategoryModel>()
        categoryList.add(CategoryModel("Pizza", "cat_1"))
        categoryList.add(CategoryModel("Burger", "cat_2"))
        categoryList.add(CategoryModel("Hotdog", "cat_3"))
        categoryList.add(CategoryModel("Drink", "cat_4"))
        categoryList.add(CategoryModel("Dount", "cat_5"))

        return categoryList
    }

    override suspend fun populatePopularList(): ArrayList<FoodModel> {

        val foodList = ArrayList<FoodModel>()
        foodList.add(FoodModel("Pepperoni pizza", "pizza1", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 9.76, null))
        foodList.add(FoodModel("Cheese Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 8.79, null))
        foodList.add(FoodModel("Vegetable pizza", "pizza2", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 8.5, null))

        return foodList
    }


}