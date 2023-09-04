package com.rozum.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}