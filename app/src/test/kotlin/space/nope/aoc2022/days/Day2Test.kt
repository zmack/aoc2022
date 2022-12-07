package space.nope.aoc2022.days

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2Test {
    val test = """
        A Y
        B X
        C Z
    """.trimIndent()

    @Test
    fun `basic case works`() {
        val games = Day2.textToGames(test)
        assertEquals(15, games.sumOf { println(it.totalScore()); it.totalScore() })
    }
}