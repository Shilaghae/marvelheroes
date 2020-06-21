package com.withplum.yourheroes.persdistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "imagePortrait")
    val imagePortrait: String?,
    @ColumnInfo(name = "imageStandard")
    val imageStandard: String?
)
