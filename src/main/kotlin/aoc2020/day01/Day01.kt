package aoc2020.day01

import utils.combinationsAsSequence

/**
 * My solution for day 1 of Advent of Code 2020.
 * The puzzle can be found at the <a href="https://adventofcode.com/2020/day/1">AoC page</a>.
 */
object Day01 {

    fun part1(input: List<Int>): Int {
        return input.combinationsAsSequence(2).find { it.sum() == 2020 }?.reduce(Int::times)
            ?: error("No combination found")
    }

    fun part2(input: List<Int>): Int {
        return input.combinationsAsSequence(3).find { it.sum() == 2020 }?.reduce(Int::times)
            ?: error("No combination found")
    }
}
