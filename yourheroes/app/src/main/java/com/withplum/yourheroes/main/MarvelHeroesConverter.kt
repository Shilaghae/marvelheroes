package com.withplum.yourheroes.main

import javax.inject.Inject

class MarvelHeroesConverter @Inject constructor() {

    fun convert(heroes: List<Hero>): UiHeroesList {
        if (heroes.isEmpty()) return UiHeroesList.None
        val uiHeroes = mutableListOf<UiHero>()
        heroes.forEach {
            uiHeroes.add(UiHero(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    circleImage =
                    if (it.imageStandard is HeroImage.Data) {
                        UiHeroImage.Data(it.imageStandard.uri)
                    } else
                        UiHeroImage.None)
            )
        }
        return UiHeroesList.Data(uiHeroes)
    }

    fun convert(hero: Hero): UiHeroDetails {
        return UiHeroDetails.Data(
                id = hero.id,
                name = hero.name,
                description = hero.description,
                image = if (hero.imagePortrait is HeroImage.Data) {
                    UiHeroImage.Data(hero.imagePortrait.uri)
                } else
                    UiHeroImage.None,
                button = if (hero.isInSquad) UiHeroSquadButton.FireFromSquad
                else UiHeroSquadButton.HireToSquad)
    }
}