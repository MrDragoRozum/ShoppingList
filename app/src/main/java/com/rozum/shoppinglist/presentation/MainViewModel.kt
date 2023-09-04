package com.rozum.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rozum.shoppinglist.data.ShopListRepositoryImpl
import com.rozum.shoppinglist.domain.EditShopItemUseCase
import com.rozum.shoppinglist.domain.GetShopListUseCase
import com.rozum.shoppinglist.domain.RemoveShopItemUseCase
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.domain.ShopListRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShopListRepository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)

    val getShopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }

    fun changeEnabledStatus(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }

}