package com.samng.youconverter.presenter

import com.samng.injector.youconverter.network.ConvertUseCaseInjector.convertUseCase
import com.samng.model.Convertion
import com.samng.youconverter.ConverterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConverterPresenter(val converterView: ConverterView) {

    fun startPresenting() {

        if (!isValidLink) {
            return
        }

        val movieUrl = "https://www.youtube.com/watch?v=i62Zjga8JOM"

        convertUseCase(converterView.getContext()).convertMovie(movieUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it: Convertion? ->

                }, {
                    converterView.showErrorMessgae()
                })


    }

    private val isValidLink: Boolean
        get() = true

    fun stopPresenting() {

    }
}
