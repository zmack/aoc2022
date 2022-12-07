package space.nope.aoc2022.days

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day1Test {
    val input = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

    @Test
    fun `splits things right in the example`() {
        val output = Day1.textToElf(input)
        println(output)
    }
}