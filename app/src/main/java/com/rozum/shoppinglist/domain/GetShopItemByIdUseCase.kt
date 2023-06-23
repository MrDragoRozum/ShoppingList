package com.rozum.shoppinglist.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItemById(shopItemId: Int) = shopListRepository.getShopItemById(shopItemId)
}