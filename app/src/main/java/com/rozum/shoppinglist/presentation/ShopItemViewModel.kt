package com.rozum.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.rozum.shoppinglist.data.ShopListRepositoryImpl
import com.rozum.shoppinglist.domain.AddShopItemUseCase
import com.rozum.shoppinglist.domain.EditShopItemUseCase
import com.rozum.shoppinglist.domain.GetShopItemByIdUseCase
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.domain.ShopListRepository

class ShopItemViewModel : ViewModel() {

    private val shopListRepository: ShopListRepository = ShopListRepositoryImpl
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val addShopItemUseCase = AddShopItemUseCase(shopListRepository)
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(shopListRepository)

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val item = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(item)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val item = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(item)
        }
    }

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemByIdUseCase.getShopItemById(shopItemId)
    }

    private fun parseName(inputName: String?) = inputName?.trim() ?: ""
    private fun parseCount(inputCount: String?) = inputCount?.toIntOrNull() ?: 0
    private fun validateInput(inputName: String, inputCount: Int): Boolean {
        var result = true
        if (inputName.isBlank()) {
            // TODO: Показать ошибку в inputName
            result = false
        }
        if (inputCount <= 0) {
            // TODO: Показать ошибку в inputCount
            result = false
        }
        return result
    }
}