package com.withplum.yourheroes.detail

import com.withplum.yourheroes.main.MarvelHeroesConverter
import com.withplum.yourheroes.main.UiHeroDetails
import com.withplum.yourheroes.main.id
import com.withplum.yourheroes.main.flipHiredOrFired
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroDetailsInteractor @Inject constructor(
    private val repository: HeroDetailsRepository,
    private val converter: MarvelHeroesConverter
) {
    private lateinit var hero: UiHeroDetails

    private val onUiModelChanged = PublishSubject.create<UiHeroDetails>()

    fun getHero(idHero: String): Observable<UiHeroDetails> {
        return repository.getHero(idHero)
                .map { converter.convert(it) }
                .doOnNext { hero = it }
                .startWith(UiHeroDetails.Void)
    }

    fun fireHero(): Completable {
        return hero.id()?.let {
            repository.fireHero(it)
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .doOnComplete { onUiModelChanged.onNext(hero.flipHiredOrFired()) }
        } ?: Completable.complete()
    }

    fun hireHero(): Completable {
        return repository.hireHero()
                .doOnError { throwable -> throwable.printStackTrace() }
                .doOnComplete { onUiModelChanged.onNext(hero.flipHiredOrFired()) }
    }

    fun onUiModelChanged(): Observable<UiHeroDetails> = onUiModelChanged
}