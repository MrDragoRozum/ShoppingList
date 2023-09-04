package com.rozum.shoppinglist.domain

data class ShopItem(
    val name: String,
    val score: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
