package com.withplum.yourheroes.detail

import androidx.lifecycle.MutableLiveData
import com.withplum.yourheroes.base.BaseViewModel
import com.withplum.yourheroes.base.SchedulerProvider
import com.withplum.yourheroes.main.UiHeroDetails
import javax.inject.Inject

class HeroDetailsViewModel @Inject constructor(
    private val interactor: HeroDetailsInteractor,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val uiHeroLiveData = MutableLiveData<UiHeroDetails>()
    val uiModelError = MutableLiveData<Boolean>()

    fun init(idHero: String) {
        disposeOnClear(interactor.getHero(idHero)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                            uiHeroLiveData.value = it
                        }, {
                            uiModelError.value = true
                        }
                ))

        disposeOnClear(interactor.onUiModelChanged()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    uiHeroLiveData.value = it
                })
    }

    fun fireHero() {
        disposeOnClear(interactor.fireHero()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe()
        )
    }

    fun hireHero() {
        disposeOnClear(interactor.hireHero()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe()
        )
    }
}