package com.rozum.shoppinglist.presention

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var item: ShopItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getShopList.observe(this) {
            Log.d("MainActivity", it.toString())
        }
        viewModel.removeShopItem(ShopItem("item 1", 1, true, 1))

        viewModel.changeEnabledStatus(ShopItem("item 2", 2, true, 2))
    }
}