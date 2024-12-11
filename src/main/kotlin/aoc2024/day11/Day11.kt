package aoc2024.day11

import utils.split

/**
 * My solution for day 11 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/11">AoC page</a>.
 */
object Day11 {

    fun part1(input: List<Long>): Int {
        return applyRules(input, 25).count()
    }

    fun part2(input: List<Long>): Int {
        return 0
    }

}

private fun applyRules(rocks: List<Long>, numberOfBlinks: Int): List<Long> {
    var newRocks = rocks
    repeat(numberOfBlinks) {
        newRocks = newRocks.flatMap { blink(it) }
    }
    return newRocks
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
