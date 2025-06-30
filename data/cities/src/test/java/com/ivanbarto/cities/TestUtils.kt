package com.ivanbarto.cities

import okio.buffer
import okio.source

object TestUtils {
    fun getJsonFromFileAsString(fileName: String): String {
        return javaClass.classLoader?.getResourceAsStream(fileName)?.source()?.buffer()
            ?.readString(Charsets.UTF_8) ?: ""
    }
}