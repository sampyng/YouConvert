package com.samng.youconverter.network

import android.content.Context
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkHttpClient(val context: Context) : HttpClient {
    val client: OkHttpClient = OkHttpClient()

    override fun fetchDownloadLink(url: String): Response {

        val request = Request.Builder()
                .url(FETCH_TRACK_URL + url)
                .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            return response
        } else {
            throw Exception("Network error " + response.code())
        }
    }

    override fun download(url: String, filename: String) {
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw IOException("Failed to download file: " + response)
        }
        val fos = context.openFileOutput(filename, Context.MODE_PRIVATE)
        fos.write(response.body()!!.bytes())
        fos.close()
    }

    companion object {
        private val JSON = MediaType.parse("application/json; charset=utf-8")
        private val FETCH_TRACK_URL = "http://www.youtubeinmp3.com/fetch/?format=JSON&video="
    }
}
