package com.withplum.yourheroes.main

import android.net.Uri
import com.withplum.yourheroes.persdistance.HeroEntity
import javax.inject.Inject

class MySquadHeroesMapper @Inject constructor() {

    fun map(heroes: List<HeroEntity>): List<Hero> {
        val thisHeroes = mutableListOf<Hero>()
        heroes.forEach { it ->
            thisHeroes.add(Hero(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    imagePortrait = it.imagePortrait?.let { im ->
                        HeroImage.Data(Uri.parse(im))
                    } ?: HeroImage.None,
                    imageStandard = it.imageStandard?.let { im ->
                        HeroImage.Data(Uri.parse(im))
                    } ?: HeroImage.None,
                    isInSquad = false
            ))
        }
        return thisHeroes
    }

    fun map(hero: HeroEntity): Hero {
        return Hero(
                id = hero.id,
                name = hero.name,
                description = hero.description,
                isInSquad = true,
                imagePortrait = hero.imagePortrait?.let { im ->
                    HeroImage.Data(Uri.parse(im))
                } ?: HeroImage.None,
                imageStandard = hero.imageStandard?.let { im ->
                    HeroImage.Data(Uri.parse(im))
                } ?: HeroImage.None
        )
    }

    fun map(hero: Hero): HeroEntity {
        return HeroEntity(
                id = hero.id,
                name = hero.name,
                description = hero.description,
                imagePortrait = if (hero.imagePortrait is HeroImage.Data) hero.imagePortrait.uri.toString() else null,
                imageStandard = if (hero.imageStandard is HeroImage.Data) hero.imageStandard.uri.toString() else null
        )
    }
}