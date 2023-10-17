package com.rozum.shoppinglist.data

import com.rozum.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {
    fun mapEntityTopDbModel(shopItem: ShopItem) =
        ShopItemDbModel(shopItem.id, shopItem.name, shopItem.score, shopItem.enabled)

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        shopItemDbModel.name,
        shopItemDbModel.score,
        shopItemDbModel.enabled,
        shopItemDbModel.id
    )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) =
        list.map { mapDbModelToEntity(it) }
}