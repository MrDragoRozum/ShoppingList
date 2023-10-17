package com.rozum.shoppinglist.di

import com.rozum.shoppinglist.data.ShopListRepositoryImpl
import com.rozum.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository
}