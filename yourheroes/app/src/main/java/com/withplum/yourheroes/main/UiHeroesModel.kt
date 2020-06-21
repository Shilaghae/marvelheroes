package com.withplum.yourheroes.main

import android.net.Uri
import androidx.annotation.DrawableRes
import com.withplum.yourheroes.R
import com.withplum.yourheroes.util.StringSpecification

data class UiHeroesModel(
    val squad: UiHeroesList,
    val heroes: UiHeroesList,
    val errorMessage: StringSpecification
)

sealed class UiHeroesList {
    data class Data(
        val heroes: List<UiHero>
    ) : UiHeroesList()

    object None : UiHeroesList()
}

class UiHero(
    val id: String,
    val name: String,
    val description: String,
    val circleImage: UiHeroImage
)

sealed class UiHeroDetails {

    data class Data(
        val id: String,
        val name: String,
        val description: String,
        val image: UiHeroImage,
        var button: UiHeroSquadButton
    ) : UiHeroDetails()

    object Void : UiHeroDetails()
}

fun UiHeroDetails.flipHiredOrFired(): UiHeroDetails {
    return when (this) {
        is UiHeroDetails.Data -> {
            this.copy(button = when (this.button) {
                is UiHeroSquadButton.FireFromSquad -> UiHeroSquadButton.HireToSquad
                else -> UiHeroSquadButton.FireFromSquad
            })
        }
        else -> {
            UiHeroDetails.Void
        }
    }
}

fun UiHeroDetails.id(): String? {
    return when (this) {
        is UiHeroDetails.Data -> this.id
        else -> null
    }
}

sealed class UiHeroSquadButton(
    var text: StringSpecification,
    @DrawableRes var background: Int
) {
    object HireToSquad : UiHeroSquadButton(StringSpecification.Resource(R.string.hire_to_squad,
            String(Character.toChars(0x1F4AA))), R.drawable.hire_button)

    object FireFromSquad : UiHeroSquadButton(StringSpecification.Resource(R.string.fire_to_squad,
            String(Character.toChars(0x1F525))), R.drawable.fire_button)
}

sealed class UiHeroImage {
    object None : UiHeroImage()
    class Data(val bitmap: Uri) : UiHeroImage()
}