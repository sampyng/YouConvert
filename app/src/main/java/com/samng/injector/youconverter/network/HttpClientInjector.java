package com.samng.injector.youconverter.network;

import com.samng.youconverter.network.NetworkHttpClient;

public class HttpClientInjector {
    public static com.samng.youconverter.network.HttpClient httpClient() {
        return new NetworkHttpClient();
    }
}
