package com.samng.youconverter.presenter

import android.text.TextUtils.isEmpty
import android.webkit.URLUtil.isValidUrl
import com.samng.injector.youconverter.network.ConvertUseCaseInjector.convertUseCase
import com.samng.model.Convertion
import com.samng.youconverter.ConverterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConverterPresenter(val converterView: ConverterView) {

    fun startPresenting(text: String?) {

        if (!isValidLink(text)) {
            converterView.showErrorMessgae()
            return
        }

        convertUseCase(converterView.getContext()).convertMovie(text!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it: Convertion? ->
                    converterView.showSuccessMessgae(it!!.title)
                }, {
                    converterView.showErrorMessgae()
                })
    }

    fun stopPresenting() {

    }

    internal fun isValidLink(text: String?): Boolean {
        if (isEmpty(text)) {
            return false
        }
        if (!isValidUrl(text)) {
            return false
        }
        return true
    }
}
