package com.withplum.yourheroes.base

import com.withplum.yourheroes.detail.HeroDetailsActivity
import com.withplum.yourheroes.main.YouHeroesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun contributeYouHeroesActivity() : YouHeroesActivity

    @ContributesAndroidInjector
    abstract fun contributeHeroDetailsActivity() : HeroDetailsActivity
}