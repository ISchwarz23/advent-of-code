package aoc2024.day03

import utils.split

/**
 * My solution for day 3 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/3">AoC page</a>.
 */
object Day03 {

    fun part1(input: List<String>): Int {
        return input.flatMap { extractMultiplications(it) }.sumOf { it.first * it.second }
    }

    fun part2(input: List<String>): Int {
        return input.joinToString("").split("do()")
            .map {
                var endIndex = it.indexOf("don't()")
                if(endIndex == -1) endIndex = it.length
                it.substring(0, endIndex)
            }
            .flatMap { extractMultiplications(it) }
            .sumOf { it.first * it.second }
    }

}

private fun extractMultiplications(str: String): List<Pair<Int, Int>> {
    return "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
        .findAll(str).map { Pair(it.groupValues[1].toInt(), it.groupValues[2].toInt()) }
        .toList()
}
