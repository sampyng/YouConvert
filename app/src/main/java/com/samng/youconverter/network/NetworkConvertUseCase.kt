package com.samng.youconverter.network

import com.samng.injector.youconverter.network.HttpClientInjector.httpClient
import com.samng.model.Convertion
import io.reactivex.Flowable
import okhttp3.Response
import org.json.JSONObject

class NetworkConvertUseCase : ConvertUseCase {
    override fun convertMovie(url: String): Flowable<Convertion> {
        return Flowable.fromCallable({
            httpClient().fetchDownloadLink(url)
        }).map({
            it: Response? ->
            val response = JSONObject(it!!.body()!!.string())
            Convertion.create {
                title { response.getString("title") }
                length { response.getInt("length") }
                url { response.getString("link") }
            }
        })
    }
}