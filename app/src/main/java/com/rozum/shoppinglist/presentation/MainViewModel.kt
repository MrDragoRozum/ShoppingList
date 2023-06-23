package com.rozum.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.rozum.shoppinglist.data.ShopListRepositoryImpl
import com.rozum.shoppinglist.domain.EditShopItemUseCase
import com.rozum.shoppinglist.domain.GetShopListUseCase
import com.rozum.shoppinglist.domain.RemoveShopItemUseCase
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.domain.ShopListRepository

class MainViewModel : ViewModel() {

    private val repository: ShopListRepository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)

    val getShopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun changeEnabledStatus(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}