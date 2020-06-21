package com.withplum.yourheroes.main

import android.net.Uri
import com.withplum.yourheroes.exception.ApiMappingException
import com.withplum.yourheroes.network.ApiCharacterDataWrapper
import java.lang.StringBuilder
import javax.inject.Inject

class MarvelHeroesMapper @Inject constructor() {

    fun map(api: ApiCharacterDataWrapper): List<Hero> {
        val heroes: MutableList<Hero> = mutableListOf()
        api.data?.results?.forEach {
            heroes.add(Hero(
                    id = (it.id ?: throw ApiMappingException("ID of the hero cannot be null")).toString(),
                    name = (it.name ?: "").toString(),
                    description = (it.description ?: "").toString(),
                    imagePortrait = it.thumbnail?.let { th ->
                        if (th.path != null && th.extension != null) {
                            val h = StringBuilder(th.path).append(HERO_IMAGE_XLARGE).append(th.extension)
                            HeroImage.Data(Uri.parse(h.toString()))
                        } else
                            HeroImage.None
                    } ?: HeroImage.None,
                    imageStandard = it.thumbnail?.let { th ->
                        if (th.path != null && th.extension != null) {
                            val h = StringBuilder(th.path).append(HERO_IMAGE_STANDARD_MEDIUM).append(th.extension)
                            HeroImage.Data(Uri.parse(h.toString()))
                        } else
                            HeroImage.None
                    } ?: HeroImage.None,
                    isInSquad = false
            ))
        }
        return heroes
    }
}

private const val HERO_IMAGE_XLARGE = "/standard_fantastic."
private const val HERO_IMAGE_STANDARD_MEDIUM = "/standard_medium."