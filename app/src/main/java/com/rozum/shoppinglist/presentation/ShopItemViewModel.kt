package com.rozum.shoppinglist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rozum.shoppinglist.data.ShopListRepositoryImpl
import com.rozum.shoppinglist.domain.AddShopItemUseCase
import com.rozum.shoppinglist.domain.EditShopItemUseCase
import com.rozum.shoppinglist.domain.GetShopItemByIdUseCase
import com.rozum.shoppinglist.domain.ShopItem
import com.rozum.shoppinglist.domain.ShopListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val shopListRepository: ShopListRepository = ShopListRepositoryImpl(application)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val addShopItemUseCase = AddShopItemUseCase(shopListRepository)
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(shopListRepository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = _errorInputName
    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount
    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem> get() = _shopItem
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit> get() = _shouldCloseScreen

    private val scope = CoroutineScope(Dispatchers.IO)

    fun addShopItem(inputName: String, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            scope.launch {
                val item = ShopItem(name, count, true)
                addShopItemUseCase.addShopItem(item)
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                scope.launch {
                    val shopItem = it.copy(name = name, score = count)
                    editShopItemUseCase.editShopItem(shopItem)
                    finishWork()
                }
            }
        }
    }

    fun getShopItem(shopItemId: Int) {
        scope.launch {
            val item = getShopItemByIdUseCase.getShopItemById(shopItemId)
            Log.d("ShopItemViewModel", "getShopItem: $item")
            _shopItem.value = item
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun parseName(inputName: String?) = inputName?.trim() ?: ""
    private fun parseCount(inputCount: String?) = inputCount?.toIntOrNull() ?: 0
    private fun validateInput(inputName: String, inputCount: Int): Boolean {
        var result = true
        if (inputName.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (inputCount <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}