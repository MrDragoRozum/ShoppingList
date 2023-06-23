package com.rozum.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var linerLayoutItemsShop: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linerLayoutItemsShop = findViewById(R.id.linerLayoutItemsShop)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getShopList.observe(this) { showList(it) }
    }

    private fun showList(list: List<ShopItem>) {
        linerLayoutItemsShop.removeAllViews()
        for(item in list) {
            val layout = if(item.enabled) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }
            val view = LayoutInflater.from(this).inflate(layout, linerLayoutItemsShop, false)
            val textViewName = view.findViewById<TextView>(R.id.textViewName)
            val textViewCount = view.findViewById<TextView>(R.id.textViewCount)
            textViewName.text = item.name
            textViewCount.text = item.score.toString()
            view.setOnLongClickListener {
                viewModel.changeEnabledStatus(item)
                true
            }
            linerLayoutItemsShop.addView(view)
        }
    }
}