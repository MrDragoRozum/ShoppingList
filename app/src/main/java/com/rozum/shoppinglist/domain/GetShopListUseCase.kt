package com.rozum.shoppinglist.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList() = shopListRepository.getShopList()
}