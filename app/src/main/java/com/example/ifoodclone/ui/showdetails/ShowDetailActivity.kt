package com.example.ifoodclone.ui.showdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ifoodclone.databinding.ActivityShowDetailBinding
import com.example.ifoodclone.helper.ManagementCart
import com.example.ifoodclone.models.FoodModel
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

class ShowDetailActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityShowDetailBinding
    private lateinit var foodObject: FoodModel
    private val managementCart: ManagementCart by inject()
    private var numberOrder = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        foodObject = intent.getParcelableExtra<FoodModel>("result")!!
        getBundle()
        setClicks()
    }

    private fun getBundle() {

        val drawableResourceId = this.resources.getIdentifier(foodObject.pic, "drawable", this.packageName)

        Glide.with(this)
                .load(drawableResourceId)
                .into(binding.foodPic)

        binding.titleTxt.text = foodObject.title
        binding.priceTxt.text = "$" + foodObject.fee.toString()
        binding.descriptionTxt.text = foodObject.description
        binding.numberOrderTxt.text = numberOrder.toString()
    }

    private fun setClicks() {
        binding.plusBtn.setOnClickListener {
            numberOrder += 1
            binding.numberOrderTxt.text = numberOrder.toString()
        }

        binding.minusBtn.setOnClickListener {

            if (numberOrder > 1) {
                numberOrder -= 1
            }
            binding.numberOrderTxt.text = numberOrder.toString()
        }
        binding.addToCardBtn.setOnClickListener {

            foodObject.numberInCart = numberOrder
            foodObject.let { it1 -> managementCart.insertFood(it1) }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
