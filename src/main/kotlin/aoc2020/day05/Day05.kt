package aoc2020.day05

import utils.split

/**
 * My solution for day 5 of Advent of Code 2020.
 * The puzzle can be found at the <a href="https://adventofcode.com/2020/day/5">AoC page</a>.
 */
object Day05 {

    fun part1(input: List<String>): Int {
        return input.maxOf { getSeatId(it) }
    }

    fun part2(input: List<String>): Int {
        val neighbourIds = input.map { getSeatId(it) }
            .sorted()
            .windowed(2)
            .find { it[1] - it[0] == 2 } ?: error("Neighbours not found")
        return neighbourIds[0] + 1
    }

}

private fun getSeatId(descriptor: String): Int {
    val (rowDescriptor, columnDescriptor) = descriptor.split(descriptor.length - 3)
    var rowRange = 0..127
    for (c in rowDescriptor.toCharArray()) {
        val width = (rowRange.last - rowRange.first) / 2
        when (c) {
            'F' -> rowRange = rowRange.first..(rowRange.first + width)
            'B' -> rowRange = (rowRange.last - width)..rowRange.last
        }
    }
    var columnRange = 0..7
    for (c in columnDescriptor.toCharArray()) {
        val width = (columnRange.last - columnRange.first) / 2
        when (c) {
            'L' -> columnRange = columnRange.first..(columnRange.first + width)
            'R' -> columnRange = (columnRange.last - width)..columnRange.last
        }
    }
    return rowRange.first * 8 + columnRange.first
}
