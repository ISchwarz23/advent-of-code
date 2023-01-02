package aoc2020.day02

/**
 * My solution for day 2 of Advent of Code 2020.
 * The puzzle can be found at the <a href="https://adventofcode.com/2020/day/2">AoC page</a>.
 */
object Day02 {

    fun part1(input: List<String>): Int {
        return input.map { PasswordDbEntry.of(it) }.count { it.isValidByRange }
    }

    fun part2(input: List<String>): Int {
        return input.map { PasswordDbEntry.of(it) }.count { it.isValidByIndex }
    }

}

