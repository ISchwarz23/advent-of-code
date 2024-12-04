package aoc2024.day04

import utils.Vector2
import utils.indexesOf

/**
 * My solution for day 4 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/4">AoC page</a>.
 */
object Day04 {

    fun part1(input: List<List<Char>>): Int {
        return input.flatMapIndexed{ y, line -> line.indexesOf('X').map { index -> Vector2(index, y) } }
            .flatMap { coordsOfX -> getAllStringsFromCharGrid(input, coordsOfX, 4) }
            .count { it == "XMAS" }
    }

    fun part2(input: List<List<Char>>): Int {
        return input.flatMapIndexed{ y, line -> line.indexesOf('A').map { x -> Vector2(x, y) } }
            .count { coordsOfM -> getDiagonalStringsFromCharGrid(input, coordsOfM, 3).count { word -> word == "MAS" } == 2 }
    }

}



val DOWN = Vector2(0, 1)
val RIGHT = Vector2(1, 0)
val UP = Vector2(0, -1)
val LEFT = Vector2(-1, 0)
val MAIN_DIRECTIONS = listOf(DOWN, RIGHT, UP, LEFT)

val DOWN_RIGHT = DOWN + RIGHT
val DOWN_LEFT = DOWN + LEFT
val UP_LEFT = UP + LEFT
val UP_RIGHT = UP + RIGHT
val DIAGONAL_DIRECTIONS = listOf(DOWN_RIGHT, DOWN_LEFT, UP_LEFT, UP_RIGHT)

val ALL_DIRECTIONS = MAIN_DIRECTIONS + DIAGONAL_DIRECTIONS



private fun getAllStringsFromCharGrid(grid: List<List<Char>>, start: Vector2, length: Int): List<String> {
    return ALL_DIRECTIONS.map { direction -> getStringFromCharGrid(grid, start, length, direction) }
}

private fun getDiagonalStringsFromCharGrid(grid: List<List<Char>>, center: Vector2, length: Int): List<String> {
    val noOfSteps = length / 2
    return DIAGONAL_DIRECTIONS.map { diagonalDirection ->
        val start = Vector2(center.x - noOfSteps * diagonalDirection.x, center.y - noOfSteps * diagonalDirection.y);
        getStringFromCharGrid(grid, start, length, diagonalDirection)
    }
}

private fun getStringFromCharGrid(grid: List<List<Char>>, start: Vector2, length: Int, step: Vector2): String {
    var str = ""
    var pos = start

    repeat (length) {
        try {
            str += grid[pos.y.toInt()][pos.x.toInt()]
            pos += step
        } catch (t: Throwable) {
            // do nothing
        }
    }

    return str
}