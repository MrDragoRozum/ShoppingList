package com.rozum.shoppinglist.domain

data class ShopItem(
    val id: Int,
    val name: String,
    val score: Int,
    val enabled: Boolean
)
