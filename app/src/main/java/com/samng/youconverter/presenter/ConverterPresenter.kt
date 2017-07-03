package com.samng.youconverter.presenter

import com.samng.injector.youconverter.network.ConvertUseCaseInjector.convertUseCase
import com.samng.model.Convertion
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConverterPresenter {

    fun startPresenting() {

        if (!isValidLink) {
            return
        }

        val movieUrl = "https://www.youtube.com/watch?v=i62Zjga8JOM"

        convertUseCase().convertMovie(movieUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it: Convertion? ->

                })
    }

    private val isValidLink: Boolean
        get() = true

    fun stopPresenting() {

    }
}
