package com.samng.injector.youconverter.network;

import android.content.Context;

import com.samng.youconverter.network.NetworkHttpClient;

public class HttpClientInjector {
    public static com.samng.youconverter.network.HttpClient httpClient(Context context) {
        return new NetworkHttpClient(context);
    }
}
