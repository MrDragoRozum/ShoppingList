package com.rozum.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopItemDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityTopDbModel(shopItem))
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityTopDbModel(shopItem))
    }

    override fun getShopItemById(shopItemId: Int) =
        mapper.mapDbModelToEntity(shopItemDao.getShopItem(shopItemId))

    override fun getShopList(): LiveData<List<ShopItem>> =
        shopItemDao.getShopItemList().map { mapper.mapListDbModelToListEntity(it) }

    override fun removeShopItem(shopItem: ShopItem) {
        shopItemDao.removeShopItem(shopItem.id)
    }
}