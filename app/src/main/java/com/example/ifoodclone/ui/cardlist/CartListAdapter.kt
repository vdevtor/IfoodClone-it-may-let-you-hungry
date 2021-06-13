package com.example.ifoodclone.ui.cardlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ifoodclone.base.ChangeNumberItemsListener
import com.example.ifoodclone.databinding.ViewholderCardBinding
import com.example.ifoodclone.helper.ManagementCart
import com.example.ifoodclone.models.FoodModel
import java.lang.String
import kotlin.math.roundToInt

class CartListAdapter(
        private val foodList: ArrayList<FoodModel>,
        context: Context,
        private val changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    private var managementCart = ManagementCart(context)

    inner class ViewHolder(val binding: ViewholderCardBinding) : RecyclerView.ViewHolder(binding.root) {

        var totalEachItem: TextView? = null
        fun bind(foodItem: FoodModel) {
            totalEachItem = binding.totalEachItem
            binding.title2Txt.text = foodItem.title
            binding.feeEachItem.text = foodItem.fee.toString()
            binding.numberItemTxt.text = foodItem.numberInCart.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(foodList[position])
        holder.totalEachItem?.text = String.valueOf(((foodList[position].numberInCart
                ?.times(foodList[position].fee!!)
                ?: 0.0) * 100.0).roundToInt() / 100.0)

        val drawableResourceId = holder.itemView.context.resources
                .getIdentifier(foodList[position].pic, "drawable",
                        holder.itemView.context.packageName)
        with(holder) {
            with(binding) {

                Glide.with(holder.itemView.context)
                        .load(drawableResourceId)
                        .into(picCard)


                plusCardBtn.setOnClickListener {
                    managementCart.plusNumberFood(foodList, position, object : ChangeNumberItemsListener {
                        override fun changed() {
                            notifyDataSetChanged()
                            changeNumberItemsListener.changed()
                        }

                    })
                }
                minusCardBtn.setOnClickListener {

                    managementCart.MinusNumerFood(foodList, position, object : ChangeNumberItemsListener {
                        override fun changed() {
                            notifyDataSetChanged()
                            changeNumberItemsListener.changed()
                        }
                    })

                }
            }
        }

    }

    override fun getItemCount() = foodList.size
}