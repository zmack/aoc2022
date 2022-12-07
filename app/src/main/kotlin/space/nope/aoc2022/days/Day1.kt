package space.nope.aoc2022.days

import space.nope.aoc2022.common.Utils
import java.io.File
import java.util.PriorityQueue

data class Elf(
    val calories: List<Long>,
    val totalCalories: Long = calories.sum()
)

object Day1 {
    fun run() {
        val elves = Utils.loadFileResource("puzzle-input-1.txt") {
            textToElf(it)
        } ?: return

        val maxElf = elves.maxBy { it.totalCalories }
        println(maxElf)
        println(topThreeElves(elves).sumOf { it.totalCalories })
    }

    private fun topThreeElves(elves: List<Elf>): List<Elf> {
        val priorityQueue: PriorityQueue<Elf> = PriorityQueue(3) { left, right ->
            left.totalCalories.compareTo(right.totalCalories)
        }

        elves.forEach { elf ->
            priorityQueue.offer(elf)
            if (priorityQueue.size > 3) {
                priorityQueue.remove()
            }
        }

        return priorityQueue.reversed().toList()
    }

    fun textToElf(text: String): List<Elf> =
        text.lines().fold(mutableListOf<MutableList<Long>>(mutableListOf())) { acc, item ->
            if (item.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.last().add(item.toLong())
            }
            acc
        }.map {
            Elf(it)
        }
}
