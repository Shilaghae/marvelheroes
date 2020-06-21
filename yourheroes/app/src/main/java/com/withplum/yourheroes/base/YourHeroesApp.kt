package com.withplum.yourheroes.base

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class YourHeroesApp : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
    override fun applicationInjector(): AndroidInjector<YourHeroesApp> {
        return DaggerAppComponent.factory()
                .create(this)
    }
}