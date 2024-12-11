package aoc2024.day11

import utils.split

/**
 * My solution for day 11 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/11">AoC page</a>.
 */
object Day11 {

    fun part1(input: List<Long>): Long {
        return solve(input, 25)
    }

    fun part2(input: List<Long>): Long {
        return solve(input, 75)
    }

}

private fun solve(rocks: List<Long>, numberOfBlinks: Int): Long {
    var rockWithNoOfOccurrence = rocks.groupBy { it }
        .mapValues { (_, rockList) -> rockList.size.toLong() }
        .toMutableMap()

    repeat(numberOfBlinks) {
        val updatedRocks = mutableMapOf<Long, Long>()
        rockWithNoOfOccurrence.forEach { (rock, noOfRocks) ->
            blink(rock).forEach { newRock ->
                updatedRocks.compute(newRock) { _, alreadyPresentNoOfNewRocks -> (alreadyPresentNoOfNewRocks ?: 0L) + noOfRocks }
            }
        }
        rockWithNoOfOccurrence = updatedRocks
    }

    return rockWithNoOfOccurrence.map { it.value }.sum()
}

private fun blink(rock: Long): List<Long> {
    if (rock == 0L) {
        return listOf(1L)
    }

    val rockStr = "$rock"
    if (rockStr.length % 2 == 0) {
        val rocks = rockStr.split(rockStr.length / 2)
        return listOf(rocks.first.toLong(), rocks.second.toLong())
    }

    return listOf(rock * 2024)
}
