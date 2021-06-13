package com.example.ifoodclone.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifoodclone.databinding.ActivityMainBinding
import com.example.ifoodclone.helper.OnClickContract
import com.example.ifoodclone.models.FoodModel
import com.example.ifoodclone.ui.cardlist.CartListActivity
import com.example.ifoodclone.ui.showdetails.ShowDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : AppCompatActivity(), OnClickContract {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeList()
        populateCategory()
        populatePopularList()
        setUpBottomNavigation()

    }

    private fun initializeList() {
        viewModel.getPopularFoods()
        viewModel.getCategories()
    }

    private fun populatePopularList() {
        viewModel.popularFoodOnResult.observe(this, { foodlist ->
            val adapter = PopularAdapter(foodlist,this)
            recyclerViewPopular(adapter)
        })

    }

    private fun recyclerViewPopular(popularAdapter: PopularAdapter) {

        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }


    private fun populateCategory() {
        viewModel.categoriesOnResult.observe(this, { categories ->
            val adapter = CategoryAdapter(categories)
            recyclerViewCategory(adapter)
        })
    }

    private fun recyclerViewCategory(categoryAdapter: CategoryAdapter) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setUpBottomNavigation() {

        binding.cardBtn.setOnClickListener {
            startActivity(Intent(this, CartListActivity::class.java))

        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onClick(item: FoodModel) {
        val intent = Intent(this, ShowDetailActivity::class.java)
        intent.putExtra("result", item)
        startActivity(intent)
    }

}