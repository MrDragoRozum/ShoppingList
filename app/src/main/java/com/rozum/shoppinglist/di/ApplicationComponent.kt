package com.rozum.shoppinglist.di

import android.app.Application
import com.rozum.shoppinglist.presentation.MainActivity
import com.rozum.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModel::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: ShopItemFragment)
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}