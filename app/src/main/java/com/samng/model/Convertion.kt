package com.samng.model

class Convertion private constructor(val title: String, val length: Int, val url: String) {

    private constructor(builder: Builder): this(builder.title, builder.length, builder.url)

    companion object {
        fun create(init: Builder.() -> Unit) = Builder(init).build()
    }

    class Builder private constructor() {

        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        lateinit var title: String
        var length: Int = 0
        lateinit var url: String


        fun title(init: Builder.() -> String) = apply { title = init() }

        fun length(init: Builder.() -> Int) = apply { length = init() }

        fun url(init: Builder.() -> String) = apply { url = init() }

        fun build() = Convertion(this)
    }
}
