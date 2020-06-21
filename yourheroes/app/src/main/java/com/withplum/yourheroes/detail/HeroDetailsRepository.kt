package com.withplum.yourheroes.detail

import com.withplum.yourheroes.exception.NoHeroException
import com.withplum.yourheroes.main.Hero
import com.withplum.yourheroes.main.MarvelHeroesMapper
import com.withplum.yourheroes.main.MySquadHeroesMapper
import com.withplum.yourheroes.network.ApiInterface
import com.withplum.yourheroes.persdistance.HeroesDatabase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroDetailsRepository @Inject constructor(
    private val api: ApiInterface,
    private val apiMapper: MarvelHeroesMapper,
    private val dbMapper: MySquadHeroesMapper,
    private val db: HeroesDatabase
) {

    private lateinit var thisHero: Hero

    fun getHero(idHero: String): Observable<Hero> {
        val fromDb = db.heroDao()
                .getHero(idHero)
                .map { HasHero(true, dbMapper.map(it)) }
                .toObservable()
                .startWith(HasHero(false, null))

        val fromApi = api.getHero(idHero)
                .map { HasHero(true, apiMapper.map(it).first()) }
                .onErrorReturn { HasHero(false, null) }
                .toObservable()

        return Observable.combineLatest(fromDb, fromApi, BiFunction { t1, t2 ->
            combine(t1, t2)
        })
    }

    private fun combine(dbHero: HasHero, apiHero: HasHero): Hero {
        if(apiHero.hasHero) {
            return when(!dbHero.hasHero) {
                true -> {
                    thisHero = apiHero.hero!!
                    thisHero
                }
                else -> {
                    thisHero = apiHero.hero!!.copy(isInSquad = true)
                    db.heroDao().addHero(dbMapper.map(thisHero))
                    return thisHero
                }
            }
        }
        else if (dbHero.hasHero) {
            return dbHero.hero!!
        }
        throw NoHeroException("No hero to show")
    }

    fun hireHero(): Completable {
        return db.heroDao().addHero(dbMapper.map(thisHero))
    }

    fun fireHero(id: String): Completable {
        return db.heroDao().deleteHero(id)
    }

    inner class HasHero(
        var hasHero: Boolean,
        var hero: Hero?
    )
}