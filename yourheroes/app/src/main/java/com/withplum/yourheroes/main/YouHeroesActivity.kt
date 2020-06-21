package com.withplum.yourheroes.main

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.withplum.yourheroes.R
import com.withplum.yourheroes.base.BaseActivity
import com.withplum.yourheroes.base.OnHeroSelectedListener
import com.withplum.yourheroes.detail.HeroDetailsActivity
import com.withplum.yourheroes.util.StringSpecification
import kotlinx.android.synthetic.main.activity_your_heroes.*
import javax.inject.Inject

class YouHeroesActivity : BaseActivity(), OnHeroSelectedListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MarvelHeroesListViewModel

    private lateinit var heroesAdapter: HeroesAdapter
    private lateinit var mysquadAdapter: MySquadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_heroes)

        viewModel = withViewModel(viewModelFactory) {
            observe(onMarvelCharactersRetrieved, ::render)
        }

        isLoading(true)

        viewModel.getHeroes()

        heroes_my_squad_recycler.apply {
            layoutManager = LinearLayoutManager(this@YouHeroesActivity, RecyclerView.HORIZONTAL, false)
            mysquadAdapter = MySquadAdapter(this@YouHeroesActivity)
            adapter = mysquadAdapter
            setHasFixedSize(true)
        }

        heroes_list.apply {
            layoutManager = LinearLayoutManager(this@YouHeroesActivity)
            heroesAdapter = HeroesAdapter(this@YouHeroesActivity)
            adapter = heroesAdapter
            setHasFixedSize(true)
        }
    }

    private fun render(heroes: UiHeroesModel) {
        if (heroes.heroes is UiHeroesList.Data) {
            isLoading(false)
            noHeroes(false)
            heroesAdapter.setHeroes(heroes.heroes.heroes)
        } else {
            isLoading(false)
            noHeroes(true)
        }

        if (heroes.squad is UiHeroesList.Data) {
            heroes_list_my_squad_group.isVisible = true
            mysquadAdapter.setMySquad(heroes.squad.heroes)
        } else {
            heroes_list_my_squad_group.isVisible = false
        }

        if (heroes.errorMessage !is StringSpecification.Null) {
            Toast.makeText(this, R.string.load_error_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLoading(loading: Boolean) {
        heroes_progress_bar.isVisible = loading
        heroes_list.isVisible = !loading
    }

    private fun noHeroes(noHeroes: Boolean) {
        heroes_list_message.isGone = !noHeroes
    }

    override fun onHeroSelected(id: String) {
        HeroDetailsActivity.start(this, id)
    }
}