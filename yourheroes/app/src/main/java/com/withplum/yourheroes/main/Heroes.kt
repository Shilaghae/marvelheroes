package com.withplum.yourheroes.main

import android.net.Uri

data class Hero(
    val id: String,
    val name: String,
    val description: String,
    val imageStandard: HeroImage,
    val imagePortrait: HeroImage,
    var isInSquad: Boolean
)

sealed class HeroImage {
    object None : HeroImage()
    data class Data(val uri: Uri) : HeroImage()
}