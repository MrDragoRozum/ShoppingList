package com.rozum.shoppinglist.domain

import javax.inject.Inject

class GetShopListUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    fun getShopList() = shopListRepository.getShopList()
}