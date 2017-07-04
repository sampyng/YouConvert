package com.samng.youconverter

import android.content.Context

interface ConverterView {

    fun getContext(): Context
    fun showErrorMessgae()
    fun showSuccessMessgae(title: String)
}