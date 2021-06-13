package com.example.ifoodclone.helper

import android.content.Context
import android.widget.Toast
import com.example.ifoodclone.base.ChangeNumberItemsListener
import com.example.ifoodclone.models.FoodModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class ManagementCart(val context: Context) : KoinComponent {
    private val tinyDb: TinyDB by inject()

    fun insertFood(item: FoodModel) {
        val listFood: ArrayList<FoodModel> = getListCard()
        var existAlready = false
        var n = 0
        for (i in listFood.indices) {
            if (listFood[i].title.equals(item.title)) {
                existAlready = true
                n = i
                break
            }
        }

        if (existAlready) {
            listFood[n].numberInCart = item.numberInCart
        } else {
            listFood.add(item)
        }

        tinyDb.putListObject("CardList", listFood)
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show()

    }

    fun getListCard(): ArrayList<FoodModel> {
        return tinyDb.getListObject("CardList") as ArrayList<FoodModel>
    }

    fun plusNumberFood(listfood: java.util.ArrayList<FoodModel>, position: Int, changeNumberItemsListener: ChangeNumberItemsListener) {
        listfood[position].numberInCart = listfood[position].numberInCart?.plus(1)
        tinyDb.putListObject("CardList", listfood)
        changeNumberItemsListener.changed()
    }

    fun MinusNumerFood(listfood: java.util.ArrayList<FoodModel>, position: Int, changeNumberItemsListener: ChangeNumberItemsListener) {
        if (listfood[position].numberInCart == 1) {
            listfood.removeAt(position)
        } else {
            listfood[position].numberInCart = listfood[position].numberInCart?.minus(1)
        }
        tinyDb.putListObject("CardList", listfood)
        changeNumberItemsListener.changed()
    }

    fun getTotalFee(): Double? {
        val listFood2: java.util.ArrayList<FoodModel> = getListCard()
        var fee = 0.0
        for (i in listFood2.indices) {
            fee += listFood2[i].fee?.times(listFood2[i].numberInCart!!) ?: 0.0
        }
        return fee
    }

}