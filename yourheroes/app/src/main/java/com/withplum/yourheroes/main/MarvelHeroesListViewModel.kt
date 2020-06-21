package com.withplum.yourheroes.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.withplum.yourheroes.base.BaseViewModel
import com.withplum.yourheroes.base.SchedulerProvider
import javax.inject.Inject

class MarvelHeroesListViewModel @Inject constructor(
    private val schedulers: SchedulerProvider,
    private val manager: MarvelHeroesListManager
) : BaseViewModel() {

    val onMarvelCharactersRetrieved = MutableLiveData<UiHeroesModel>()

    fun getHeroes() {
        disposeOnClear(manager.register()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe({
                    onMarvelCharactersRetrieved.value = it
                }, {
                    Log.e(this::class.java.name, it.message)
                })
        )
    }
}