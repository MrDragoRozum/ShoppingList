package com.rozum.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopItemDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {


    override suspend fun addShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityTopDbModel(shopItem))
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityTopDbModel(shopItem))
    }

    override suspend fun getShopItemById(shopItemId: Int) =
        mapper.mapDbModelToEntity(shopItemDao.getShopItem(shopItemId))

    override fun getShopList(): LiveData<List<ShopItem>> =
        shopItemDao.getShopItemList().map { mapper.mapListDbModelToListEntity(it) }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        shopItemDao.removeShopItem(shopItem.id)
    }
}