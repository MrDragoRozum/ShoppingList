package com.rozum.shoppinglist.presentation

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rozum.shoppinglist.R
import com.rozum.shoppinglist.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var shopListAdapter: ShopListAdapter

    private val component by lazy {
        (application as AppShoppingList).component
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getShopList.observe(this) { shopListAdapter.submitList(it) }

        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
        contentResolver.query(
            Uri.parse("content://com.rozum.shoppinglist/shop_items"),
            null,
            null,
            null,
            null
        )
    }

    private fun isOnePaneMode() = binding.containerShopItem == null

    private fun launchFragment(fragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.containerShopItem, fragment)
            .commit()
    }

    private fun setupRecyclerView() {
        binding.adapter = shopListAdapter
        with(binding.recyclerViewShopList) {
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ITEM_SHOP_ENABLED,
                ShopListAdapter.MAX_SIZE_POOL
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ITEM_SHOP_DISABLED,
                ShopListAdapter.MAX_SIZE_POOL
            )
        }
        setupOnLongClickListener()
        setupOnClickListener()
        setupSwipeListener(binding.recyclerViewShopList)
    }

    private fun setupSwipeListener(recyclerViewShopList: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(shopItem)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerViewShopList)
    }

    private fun setupOnClickListener() {
        shopListAdapter.onShopItemClick = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.onShopItemLongClick = { viewModel.changeEnabledStatus(it) }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }
}