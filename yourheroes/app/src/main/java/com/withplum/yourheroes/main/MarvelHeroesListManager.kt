package com.withplum.yourheroes.main

import com.withplum.yourheroes.R
import com.withplum.yourheroes.util.StringSpecification
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable
import javax.inject.Inject

class MarvelHeroesListManager @Inject constructor(
    private val repository: MarvelHeroesListRepository,
    private val squad: MarvelSquadHeroListRepository,
    private val converter: MarvelHeroesConverter
) {

    private var uiModel: UiHeroesModel = UiHeroesModel(
            heroes = UiHeroesList.None,
            squad = UiHeroesList.None,
            errorMessage = StringSpecification.Null)

    fun register(): Flowable<UiHeroesModel> {

        val heroes = repository.getHeroes()
                .map {
                    uiModel = uiModel.copy(heroes = converter.convert(it))
                    uiModel
                }
                .onErrorReturn {
                    uiModel = uiModel.copy(
                            errorMessage = StringSpecification.Resource(R.string.load_error_message)
                    )
                    uiModel
                }
                .toFlowable(LATEST)

        val squad = squad.getHeroesSquad()
                .map {
                    uiModel = uiModel.copy(squad = converter.convert(it))
                    uiModel
                }
                .onErrorReturn {
                    uiModel = uiModel.copy(
                            errorMessage = StringSpecification.Resource(R.string.load_error_message)
                    )
                    uiModel
                }

        return Flowable
                .merge(heroes, squad)

    }
}