package aoc2024.day22

/**
 * My solution for day 22 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/22">AoC page</a>.
 */
object Day22 {

    fun part1(input: List<Int>): Long {
        return input.map { it.toLong() }.sumOf {
                var nextRandomNumber = it
                repeat(2000) { nextRandomNumber = nextRandomNumber.nextPseudoRandomNumber }
                return@sumOf nextRandomNumber
            }
    }

    fun part2(input: List<Int>): Int {
        return 0
    }

}

private val Long.nextPseudoRandomNumber: Long
    get() {
        var nextSecretNumber = ((this * 64) xor this) % 16777216
        nextSecretNumber = ((nextSecretNumber / 32) xor nextSecretNumber) % 16777216
        return ((nextSecretNumber * 2048) xor nextSecretNumber) % 16777216
    }
