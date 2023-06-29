package com.rozum.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rozum.shoppinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textViewName: TextView = view.findViewById<TextView>(R.id.textViewName)
    val textViewCount: TextView = view.findViewById<TextView>(R.id.textViewCount)
}