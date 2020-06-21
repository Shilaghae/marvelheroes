package com.withplum.yourheroes.persdistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HeroEntity::class], version = 1)
abstract class HeroesDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao

    companion object {

        @Volatile private var INSTANCE: HeroesDatabase? = null

        fun getInstance(context: Context): HeroesDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        HeroesDatabase::class.java, "HeroDatabase.db")
                        .build()
    }
}