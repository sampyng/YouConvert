package com.samng.youconverter.network

import okhttp3.Response

interface HttpClient {

    fun fetchDownloadLink(url: String): Response

    fun download(url: String, filename: String)
}
