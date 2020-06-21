package com.withplum.yourheroes.persdistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface HeroDao {

    @Query("SELECT * FROM heroes")
    fun getHeroes(): Flowable<List<HeroEntity>>

    @Query("SELECT * FROM heroes WHERE id = :id")
    fun getHero(id: String): Flowable<HeroEntity>

    @Query("SELECT * FROM heroes WHERE id = :id")
    fun getHeroPure(id: String): HeroEntity

    @Query("SELECT * FROM heroes")
    fun getHeroesPure(): List<HeroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHero(hero: HeroEntity) : Completable

    @Query("DELETE FROM heroes WHERE id=:id")
    fun deleteHero(id: String) : Completable
}