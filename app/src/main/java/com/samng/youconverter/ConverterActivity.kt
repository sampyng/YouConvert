package com.samng.youconverter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.samng.youconverter.presenter.ConverterPresenter

class ConverterActivity : AppCompatActivity(), ConverterView {
    lateinit private var presenter: ConverterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_converter)

        presenter = ConverterPresenter(this)
    }

    override fun onResume() {
        super.onResume()

        // Get intent, action and MIME type
        val intent = intent
        val action = intent.action
        val type = intent.type
        var text: String? = null

        if (Intent.ACTION_SEND == action && type == "text/plain") {
            text = handleSendText(intent)
        }
        presenter.startPresenting(text)
    }

    override fun onPause() {
        presenter.stopPresenting()
        super.onPause()
    }

    override fun getContext(): Context {
        return this
    }

    override fun showSuccessMessgae(title: String) {
        Toast.makeText(this, title + " Convert Success", Toast.LENGTH_LONG).show()
    }

    override fun showErrorMessgae() {
        Toast.makeText(this, "Convert Failed", Toast.LENGTH_LONG).show()
    }

    internal fun handleSendText(intent: Intent): String {
        return intent.getStringExtra(Intent.EXTRA_TEXT)
    }
}

