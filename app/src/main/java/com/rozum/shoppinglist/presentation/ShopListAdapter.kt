package com.rozum.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var listShopItem = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shop_enabled, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = listShopItem[position]
        val status = if (shopItem.enabled) {
            "Active"
        } else {
            "Not active"
        }
        holder.itemView.setOnLongClickListener {
            true
        }
        if (shopItem.enabled) {
            holder.textViewName.text = "${shopItem.name} $status"
            holder.textViewCount.text = shopItem.score.toString()
            holder.textViewName.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    android.R.color.holo_red_light
                )
            )
        }
    }



    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.textViewName.text = ""
        holder.textViewCount.text = ""
        holder.textViewName.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                android.R.color.white
            )
        )
    }

    override fun getItemCount() = listShopItem.size
    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewCount = view.findViewById<TextView>(R.id.textViewCount)
    }
}