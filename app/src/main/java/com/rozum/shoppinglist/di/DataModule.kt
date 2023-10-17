package com.rozum.shoppinglist.di

import android.app.Application
import com.rozum.shoppinglist.data.AppDatabase
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    companion object {
        @Provides
        @ApplicationScope
        fun provideShopListDao(application: Application) =
            AppDatabase.getInstance(application).shopListDao()
    }
}