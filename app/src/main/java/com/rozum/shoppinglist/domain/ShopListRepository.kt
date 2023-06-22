package com.rozum.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItemById(shopItemId: Int): ShopItem
    fun getShopList(): List<ShopItem>
    fun removeShopItem(shopItem: ShopItem)

}