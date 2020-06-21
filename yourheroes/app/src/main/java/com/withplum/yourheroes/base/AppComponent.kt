package com.withplum.yourheroes.base

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class,
    ViewModelModule::class,
    NetworkModule::class,
    DatabaseModule::class
])
interface AppComponent : AndroidInjector<YourHeroesApp> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Application): AppComponent
    }
}