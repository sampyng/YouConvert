package com.samng.youconverter.network

import com.samng.model.Convertion
import io.reactivex.Flowable

interface ConvertUseCase {

    fun convertMovie(url: String): Flowable<Convertion>
}

