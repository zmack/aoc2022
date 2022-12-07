package space.nope.aoc2022.common

import java.io.File

object Utils {
    fun<T> loadFileResource(filename: String, transformer: java.util.function.Function<String,T>): T? =
        javaClass.classLoader.getResource(filename)
            ?.let { File(it.toURI()) }
            ?.readText()?.let { transformer.apply(it) }
}