package com.withplum.yourheroes.base

import androidx.lifecycle.ViewModel
import com.withplum.yourheroes.detail.HeroDetailsViewModel
import com.withplum.yourheroes.main.MarvelHeroesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MarvelHeroesListViewModel::class)
    internal abstract fun bindMarvelHeroesListViewModel(viewModel: MarvelHeroesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HeroDetailsViewModel::class)
    internal abstract fun bindHeroDetailsiewModel(viewModel: HeroDetailsViewModel): ViewModel
}