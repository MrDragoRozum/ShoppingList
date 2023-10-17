package com.rozum.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.databinding.ItemShopDisabledBinding
import com.rozum.shoppinglist.databinding.ItemShopEnabledBinding
import com.rozum.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListAdapter @Inject constructor(): ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val ITEM_SHOP_ENABLED = 100
        const val ITEM_SHOP_DISABLED = 101
        const val MAX_SIZE_POOL = 15
    }

    var onShopItemLongClick: ((shopItem: ShopItem) -> Unit)? = null
    var onShopItemClick: ((shopItem: ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = if (viewType == ITEM_SHOP_ENABLED)
            R.layout.item_shop_enabled else R.layout.item_shop_disabled
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false)
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int) = if (getItem(position).enabled)
        ITEM_SHOP_ENABLED else ITEM_SHOP_DISABLED

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        with(holder.binding) {
            root.setOnLongClickListener {
                onShopItemLongClick?.invoke(shopItem)
                true
            }
            root.setOnClickListener { onShopItemClick?.invoke(shopItem) }
            when(this) {
                is ItemShopDisabledBinding -> {
//                    textViewName.text = shopItem.name
//                    textViewCount.text = shopItem.score.toString()
                    this.shopItem = shopItem
                }
                is ItemShopEnabledBinding -> {
                    this.shopItem = shopItem
                }
            }
        }
    }
}