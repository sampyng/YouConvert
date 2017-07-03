package com.samng.youconverter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.samng.youconverter.presenter.ConverterPresenter

class ConverterActivity : AppCompatActivity() {
    lateinit var presenter: ConverterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_converter)

        presenter = ConverterPresenter()
        presenter.startPresenting()
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }
}