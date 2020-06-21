package com.withplum.yourheroes.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.withplum.yourheroes.R
import com.withplum.yourheroes.base.BaseActivity
import com.withplum.yourheroes.main.UiHeroDetails
import com.withplum.yourheroes.main.UiHeroDetails.Data
import com.withplum.yourheroes.main.UiHeroImage
import com.withplum.yourheroes.main.UiHeroSquadButton
import com.withplum.yourheroes.util.resolve
import kotlinx.android.synthetic.main.activity_hero_details.*
import javax.inject.Inject

class HeroDetailsActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: HeroDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        viewModel = withViewModel(viewModelFactory) {
            observe(uiHeroLiveData, ::render)
            observe(uiModelError, ::showError)
        }
        if (intent.hasExtra(HERO_ID)) {
            viewModel.init(intent.getStringExtra(HERO_ID)!!)
        }
        setContentView(R.layout.activity_hero_details)

        heroes_details_close.setOnClickListener {
            finish()
        }
    }

    private fun render(uiModel: UiHeroDetails) {
        heroes_details_message.isVisible = false
        when (uiModel) {
            is Data -> {
                if (uiModel.image is UiHeroImage.Data) {
                    Picasso.get()
                            .load(uiModel.image.bitmap)
                            .error(R.drawable.ic_launcher_background)
                            .into(hero_details_image)
                }
                heroes_details_my_squad_group.isVisible = true
                heroes_details_progress_bar.isVisible = false
                hero_details_name.text = uiModel.name
                hero_details_description.text = uiModel.description
                hero_details_button.text = resolve(uiModel.button.text)
                hero_details_button.background = ContextCompat.getDrawable(
                        this, uiModel.button.background)
                hero_details_button.setOnClickListener {
                    when (uiModel.button) {
                        is UiHeroSquadButton.FireFromSquad -> viewModel.fireHero()
                        is UiHeroSquadButton.HireToSquad -> viewModel.hireHero()
                    }
                }
            }
            else -> {
                heroes_details_my_squad_group.isVisible = false
                heroes_details_progress_bar.isVisible = true
            }
        }
    }

    private fun showError(showError: Boolean) {
        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
        heroes_details_progress_bar.isVisible = false
        heroes_details_message.isVisible = true
    }

    companion object {
        fun start(context: Context, idHero: String) {

            context.startActivity(Intent(context, HeroDetailsActivity::class.java).apply {
                putExtra(HERO_ID, idHero)
            })
        }

        private const val HERO_ID = "hero_id"
    }
}