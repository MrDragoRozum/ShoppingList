package com.rozum.shoppinglist.di

import android.app.Application
import com.rozum.shoppinglist.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModel::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}