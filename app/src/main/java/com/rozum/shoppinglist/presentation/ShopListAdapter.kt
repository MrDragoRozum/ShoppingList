package com.rozum.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object {
        const val ITEM_SHOP_ENABLED = 100
        const val ITEM_SHOP_DISABLED = 101
        const val MAX_SIZE_POOL = 15
    }

    private var count = 0

    var listShopItem = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder count: ${++count}")
        val layout =
            if (viewType == ITEM_SHOP_ENABLED) R.layout.item_shop_enabled else R.layout.item_shop_disabled
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int) = if (listShopItem[position].enabled)
        ITEM_SHOP_ENABLED else ITEM_SHOP_DISABLED

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = listShopItem[position]
        holder.textViewName.text = shopItem.name
        holder.textViewCount.text = shopItem.score.toString()
    }


    override fun getItemCount() = listShopItem.size
    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById<TextView>(R.id.textViewName)
        val textViewCount = view.findViewById<TextView>(R.id.textViewCount)
    }
}