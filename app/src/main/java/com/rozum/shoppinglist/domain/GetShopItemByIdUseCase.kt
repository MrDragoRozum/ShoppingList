package com.rozum.shoppinglist.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItemById(shopItemId: Int) = shopListRepository.getShopItemById(shopItemId)
}