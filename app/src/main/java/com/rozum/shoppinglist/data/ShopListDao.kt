package com.rozum.shoppinglist.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_items")
    fun getShopItemList(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shop_items")
    fun getShopItemListCourse(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItemSync(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM SHOP_ITEMS WHERE id = :shopItemId")
    suspend fun removeShopItem(shopItemId: Int)

    @Query("DELETE FROM SHOP_ITEMS WHERE id = :shopItemId")
    fun removeShopItemSync(shopItemId: Int): Int

    @Query("SELECT * FROM SHOP_ITEMS WHERE id = :shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel

    @Query("SELECT * FROM SHOP_ITEMS WHERE id = :shopItemId LIMIT 1")
    fun getShopItemSync(shopItemId: Int): ShopItemDbModel
}