package com.samng.injector.youconverter.network;

import com.samng.youconverter.network.NetworkConvertUseCase;

public class ConvertUseCaseInjector {
    public static com.samng.youconverter.network.ConvertUseCase convertUseCase() {
        return new NetworkConvertUseCase();
    }
}
