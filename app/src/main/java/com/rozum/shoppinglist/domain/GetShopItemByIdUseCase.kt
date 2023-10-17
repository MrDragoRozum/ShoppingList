package com.rozum.shoppinglist.domain

import javax.inject.Inject

class GetShopItemByIdUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItemById(shopItemId: Int) = shopListRepository.getShopItemById(shopItemId)
}