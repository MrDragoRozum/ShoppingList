package com.rozum.shoppinglist.data

import androidx.lifecycle.MutableLiveData
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val listLiveData = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val shopItemOld = getShopItemById(shopItem.id)
        shopList.remove(shopItemOld)
        addShopItem(shopItem)
    }

    override fun getShopItemById(shopItemId: Int) =
        shopList.find { shopItemId == it.id } ?: throw RuntimeException("ShopItem has null")

    override fun getShopList() = listLiveData

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    private fun updateList() {
        listLiveData.value = shopList.toList()
    }
}