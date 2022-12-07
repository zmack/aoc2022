package space.nope.aoc2022.days

import space.nope.aoc2022.common.Utils

enum class RPSPlay(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

enum class RPSExpectedOutcome(val char: Char) {
    LOSE('X') {
        override fun rpsPlay(opponentPlay: RPSPlay): RPSPlay =
            when (opponentPlay) {
                RPSPlay.ROCK -> RPSPlay.SCISSORS
                RPSPlay.PAPER -> RPSPlay.ROCK
                RPSPlay.SCISSORS -> RPSPlay.PAPER
            }
    },
    DRAW('Y') {
        override fun rpsPlay(opponentPlay: RPSPlay): RPSPlay =
            when (opponentPlay) {
                RPSPlay.ROCK -> RPSPlay.ROCK
                RPSPlay.PAPER -> RPSPlay.PAPER
                RPSPlay.SCISSORS -> RPSPlay.SCISSORS
            }
    },
    WIN('Z') {
        override fun rpsPlay(opponentPlay: RPSPlay): RPSPlay =
            when (opponentPlay) {
                RPSPlay.ROCK -> RPSPlay.PAPER
                RPSPlay.PAPER -> RPSPlay.SCISSORS
                RPSPlay.SCISSORS -> RPSPlay.ROCK
            }
    };

    abstract fun rpsPlay(opponentPlay: RPSPlay): RPSPlay

    companion object {
        private val valueMap by lazy { RPSExpectedOutcome.values().associateBy { it.char }}

        fun ofChar(c: Char): RPSExpectedOutcome = valueMap[c]!!
    }
}

data class Game(
    val opponentPlay: RPSPlay,
    val ownPlay: RPSPlay
) {
    constructor(opponentPlayChar: Char, expectedOutcome: RPSExpectedOutcome) :
            this(opponentPlayChar.toOpponentPlay(), expectedOutcome.rpsPlay(opponentPlayChar.toOpponentPlay()))

    constructor(string: String) :
            this(string[0].toOpponentPlay(), string[2].toOwnPlay())

    fun totalScore(): Int =
        when(Pair(ownPlay, opponentPlay)) {
            Pair(RPSPlay.ROCK, RPSPlay.ROCK),
            Pair(RPSPlay.PAPER, RPSPlay.PAPER),
            Pair(RPSPlay.SCISSORS, RPSPlay.SCISSORS) -> 3

            Pair(RPSPlay.PAPER, RPSPlay.ROCK),
            Pair(RPSPlay.SCISSORS, RPSPlay.PAPER),
            Pair(RPSPlay.ROCK, RPSPlay.SCISSORS) -> 6

            else -> 0
        }.let { it + ownPlay.score }

}

private fun Char.toOpponentPlay(): RPSPlay =
    when(this) {
        'A' -> RPSPlay.ROCK
        'B' -> RPSPlay.PAPER
        'C' -> RPSPlay.SCISSORS
        else -> throw(Exception("Unknown opponent play $this"))
    }

private fun Char.toOwnPlay(): RPSPlay =
    when(this) {
        'X' -> RPSPlay.ROCK
        'Y' -> RPSPlay.PAPER
        'Z' -> RPSPlay.SCISSORS
        else -> throw(Exception("Unknown opponent play $this"))
    }

object Day2 {
    fun run() {
        val games = Utils.loadFileResource("puzzle-input-2.txt") {
            textToSecretGames(it)
        } ?: return

        val totalScore = games.sumOf { it.totalScore() }

        println(totalScore)
    }

    fun textToGames(text: String): List<Game> =
        text.lines().map {
            Game(it)
        }

    fun textToSecretGames(text: String): List<Game> =
        text.lines().map {
            Game(it[0], RPSExpectedOutcome.ofChar(it[2]))
        }
}