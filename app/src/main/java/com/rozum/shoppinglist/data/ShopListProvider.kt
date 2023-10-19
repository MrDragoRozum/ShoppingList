package com.rozum.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.presentation.AppShoppingList
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy {
        (context as AppShoppingList).component
    }

    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.rozum.shoppinglist", "shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("com.rozum.shoppinglist", "shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                shopListDao.getShopItemListCourse()
            }

            GET_SHOP_ITEM_BY_ID_QUERY -> {
                null
            }

            else -> {
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                if (values == null) return null
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val score = values.getAsInteger("score")
                val enabled = values.getAsBoolean("enabled")

                shopListDao.addShopItemSync(
                    mapper.mapEntityTopDbModel(
                        ShopItem(
                            name,
                            score,
                            enabled,
                            id
                        )
                    )
                )
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopListDao.removeShopItemSync(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                if (values == null) return 0
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val score = values.getAsInteger("score")

                val oldItem = shopListDao.getShopItemSync(id)
                val newItem = oldItem.copy(name = name, score = score)

                shopListDao.addShopItemSync(newItem)
                return 1
            }
        }
        return 0
    }

    companion object {
        private const val GET_SHOP_ITEMS_QUERY = 100
        private const val GET_SHOP_ITEM_BY_ID_QUERY = 101
    }
}