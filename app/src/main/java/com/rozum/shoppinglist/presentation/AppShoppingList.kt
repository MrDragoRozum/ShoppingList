package com.rozum.shoppinglist.presentation

import android.app.Application
import com.rozum.shoppinglist.di.DaggerApplicationComponent

class AppShoppingList: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}