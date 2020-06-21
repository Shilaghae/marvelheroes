package com.withplum.yourheroes.main

import com.withplum.yourheroes.network.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class MarvelHeroesListRepository @Inject constructor(
    private val api: ApiInterface,
    private val mapper: MarvelHeroesMapper
) {

    fun getHeroes(): Observable<List<Hero>> {
        return api.getHeroCharacters()
                .toObservable()
                .map { mapper.map(it) }
    }
}