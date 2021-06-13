package com.example.ifoodclone.ui.cardlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifoodclone.R
import com.example.ifoodclone.base.ChangeNumberItemsListener
import com.example.ifoodclone.databinding.ActivityCardListBinding
import com.example.ifoodclone.helper.ManagementCart
import com.example.ifoodclone.ui.home.MainActivity
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import kotlin.math.roundToInt


class CartListActivity : AppCompatActivity(), KoinComponent {
    private var tax: Double? = null
    private val managementCart: ManagementCart by inject()
    private lateinit var binding: ActivityCardListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigation()
        initList()
        calculateCard()


    }

    private fun bottomNavigation() {

        binding.cardBtn.setOnClickListener {

            startActivity(Intent(this, CartListActivity::class.java))
            finish()
        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun calculateCard() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = ((managementCart.getTotalFee()?.times(percentTax)
                ?: 0.0) * 100.0).roundToInt() / 100.0
        val total = (((managementCart.getTotalFee()?.plus(tax!!)
                ?: 0.0) + delivery) * 100.0).roundToInt() / 100.0
        val itemTotal = (managementCart.getTotalFee()?.times(100.0))?.roundToInt()?.div(100.0)
        binding.totalFeeTxt.text = "$$itemTotal"
        binding.taxTxt.text = "$$tax"
        binding.deliveryTxt.text = "$$delivery"
        binding.totalTxt.text = "$$total"
    }

    private fun initList() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CartListAdapter(managementCart.getListCard(), this.context, object : ChangeNumberItemsListener {
                override fun changed() {
                    calculateCard()
                }
            })

            if (managementCart.getListCard().isEmpty()) {
                binding.emptyTxt.visibility = View.VISIBLE
                binding.scrollView4.visibility = View.GONE
            } else {
                binding.emptyTxt.visibility = View.GONE
                binding.scrollView4.visibility = View.VISIBLE
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}