package com.rozum.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_items")
    fun getShopItemList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM SHOP_ITEMS WHERE id = :shopItemId")
    fun removeShopItem(shopItemId: Int)

    @Query("SELECT * FROM SHOP_ITEMS WHERE id = :shopItemId LIMIT 1")
    fun getShopItem(shopItemId: Int): ShopItemDbModel
}