package com.withplum.yourheroes.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/v1/public/characters")
    fun getHeroCharacters(): Single<ApiCharacterDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getHero(@Path("characterId") id: String): Single<ApiCharacterDataWrapper>
}