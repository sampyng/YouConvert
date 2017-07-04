package com.samng.youconverter.network

import android.content.Context
import com.samng.injector.youconverter.network.HttpClientInjector.httpClient
import com.samng.model.Convertion
import io.reactivex.Flowable
import okhttp3.Response
import org.json.JSONObject

class NetworkConvertUseCase(val context: Context) : ConvertUseCase {

    override fun convertMovie(url: String): Flowable<Convertion> {
        return Flowable.fromCallable({
            httpClient(context).fetchDownloadLink(url)
        }).map({
            it: Response? ->
            val response = JSONObject(it!!.body()!!.string())
            Convertion.create {
                title { response.getString("title") }
                length { response.getInt("length") }
                url { response.getString("link") }
            }
        }).doOnNext {
            it: Convertion? ->
            if (it != null) {
                httpClient(context).download(it.url, it.title + ".mp3")
            }
        }
    }
}