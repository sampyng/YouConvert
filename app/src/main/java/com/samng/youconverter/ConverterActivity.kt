package com.samng.youconverter

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.samng.youconverter.presenter.ConverterPresenter

class ConverterActivity : AppCompatActivity(), ConverterView {
    lateinit var presenter: ConverterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_converter)

        presenter = ConverterPresenter(this)
        presenter.startPresenting()
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun getContext(): Context {
        return this
    }

    override fun showErrorMessgae() {
        Toast.makeText(this, "Convert Failed", Toast.LENGTH_LONG).show()
    }
}

