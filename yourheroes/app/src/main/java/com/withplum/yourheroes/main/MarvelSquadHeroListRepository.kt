package com.withplum.yourheroes.main

import com.withplum.yourheroes.persdistance.HeroesDatabase
import io.reactivex.Flowable
import javax.inject.Inject

class MarvelSquadHeroListRepository @Inject constructor(
    private val db: HeroesDatabase,
    private val mapper: MySquadHeroesMapper
) {

    fun getHeroesSquad(): Flowable<List<Hero>> {
        return db.heroDao().getHeroes()
                .map { mapper.map(it) }
    }
}