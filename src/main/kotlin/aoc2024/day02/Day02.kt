package aoc2024.day02

/**
 * My solution for day 2 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/2">AoC page</a>.
 */
object Day02 {

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toInt() } }
            .count { isSafe(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toInt() } }
            .count { isSafeWhenSkipping(it) }
    }

    private fun isSafe(levels: List<Int>): Boolean {
        val deltaRange = if (isAscending(levels)) 1..3 else -3..-1

        var i = 0
        while (i < levels.size - 1) {
            val delta = levels[i + 1] - levels[i]
            if (delta !in deltaRange) {
                return false
            }
            i++
        }
        return true
    }

    private fun isSafeWhenSkipping(levels: List<Int>): Boolean {
        levels.indices.forEach { index ->
            val listWithSkip = levels.toMutableList()
            listWithSkip.removeAt(index)
            if (isSafe(listWithSkip)) {
                return true
            }
        }
        return false
    }

    private fun isAscending(levels: List<Int>, noItemsToRespect: Int = 3): Boolean {
        return levels.take(noItemsToRespect).sum() / noItemsToRespect < levels.takeLast(noItemsToRespect).sum() / noItemsToRespect
    }

}
