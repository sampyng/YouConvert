package com.samng.injector.youconverter.network;

import android.content.Context;

import com.samng.youconverter.network.NetworkConvertUseCase;

public class ConvertUseCaseInjector {
    public static com.samng.youconverter.network.ConvertUseCase convertUseCase(Context context) {
        return new NetworkConvertUseCase(context);
    }
}
