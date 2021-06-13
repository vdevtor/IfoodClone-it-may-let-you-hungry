package com.example.ifoodclone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ifoodclone.R
import com.example.ifoodclone.databinding.ViewholderCatBinding
import com.example.ifoodclone.models.CategoryModel

class CategoryAdapter(
        private val categoryList: ArrayList<CategoryModel>

) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderCatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryModel) {

            binding.categoryName.text = category.title
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding = ViewholderCatBinding.inflate(
                LayoutInflater.from(parent.context), parent, false

        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position])
        var picUrl = ""
        when (position) {
            0 -> {
                picUrl = "cat_1"
                holder.binding.mainLayout.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background1)
            }
            1 -> {
                picUrl = "cat_2"
                holder.binding.mainLayout.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background2)
            }
            2 -> {
                picUrl = "cat_3"
                holder.binding.mainLayout.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background3)
            }
            3 -> {
                picUrl = "cat_4"
                holder.binding.mainLayout.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background4)
            }
            4 -> {
                picUrl = "cat_5"
                holder.binding.mainLayout.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_background5)
            }
        }
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(picUrl, "drawable", holder.itemView.context.packageName)

        with(holder) {
            with(binding) {
                Glide.with(holder.itemView.context)
                        .load(drawableResourceId)
                        .into(categoryPic)
            }
        }


    }

    override fun getItemCount() = categoryList.size


}