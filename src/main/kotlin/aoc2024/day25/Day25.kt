package aoc2024.day25

import utils.combinationsWith
import utils.split

/**
 * My solution for day 25 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/25">AoC page</a>.
 */
object Day25 {

    fun part1(input: List<String>): Int {
        val keysAndLocks = input.split { it.isEmpty() }.map { Schematics(it) }
        val locks = keysAndLocks.filter { it.isLock }
        val keys = keysAndLocks.filter { it.isKey }
        return locks.combinationsWith(keys).count { (lock, key) -> key.fits(lock) }
    }

}

private data class Schematics(val data: List<String>) {

    val height = data.size
    val width = data[0].length

    val isKey: Boolean = data.first().contains('#')
    val isLock: Boolean = !isKey

    val columnHeights: List<Int> by lazy {
        if (isKey) {
            (0 until width).map { x -> (0 until height).first { y -> data[y][x] == '.' } - 1 }
        } else {
            (0 until width).map { x -> 5 - (height - 1 downTo 0).first { y -> data[y][x] == '.' } }
        }
    }

    fun fits(other: Schematics): Boolean {
        if(this.isKey xor other.isLock) return false
        if(this.width != other.width) return false
        if(this.height != other.height) return false

        return (0 until width).map { x -> this.columnHeights[x] + other.columnHeights[x] }.all { it <= 5 }
    }

}
