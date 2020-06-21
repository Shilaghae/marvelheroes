package com.withplum.yourheroes.base

import android.content.Context
import com.withplum.yourheroes.persdistance.HeroesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideHeroesDatabase(context: Context): HeroesDatabase {
        return HeroesDatabase.getInstance(context)
    }
}