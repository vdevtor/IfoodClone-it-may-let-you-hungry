package com.example.ifoodclone.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ifoodclone.databinding.ViewholderPopularBinding
import com.example.ifoodclone.helper.OnClickContract
import com.example.ifoodclone.models.FoodModel
import com.example.ifoodclone.ui.showdetails.ShowDetailActivity
import java.util.*

class PopularAdapter(
        private val foodList: ArrayList<FoodModel>,
        private val listener: OnClickContract
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderPopularBinding) : RecyclerView.ViewHolder(binding.root) {
        var addBtn = binding.addBtn

        fun bind(foodItem: FoodModel) {

            binding.title.text = foodItem.title
            binding.fee.text = foodItem.fee.toString()
            //var pic = binding.pic

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderPopularBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)

        val drawableResourceId = holder.itemView.context.resources
                .getIdentifier(foodList[position].pic, "drawable",
                holder.itemView.context.packageName)

        with(holder) {
            with(binding.pic) {
                Glide.with(holder.itemView.context)
                        .load(drawableResourceId)
                        .into(this)
            }
        }

      holder.addBtn.setOnClickListener {
          listener.onClick(food)
      }
    }

    override fun getItemCount() = foodList.size
}