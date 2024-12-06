package aoc2024.day06

import utils.Vector2
import utils.indexesOf

/**
 * My solution for day 6 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/6">AoC page</a>.
 */
object Day06 {

    fun part1(input: List<List<Char>>): Int {
        var position = input.mapIndexed { y, fields -> Vector2(fields.indexOf('^'), y) }.find { it.x >= 0 }
            ?: throw RuntimeException("No start symbol in input")
        var movementDirection = movementDirections[0]

        val visitedPositions = mutableSetOf<Vector2>()

        while (position.y in input.indices && position.x in input[0].indices) {
            visitedPositions += position
            position += movementDirection

            if (position.y in input.indices && position.x in input[0].indices && input[position.y.toInt()][position.x.toInt()] == '#') {
                position -= movementDirection
                movementDirection = turnRight(movementDirection)
            }
        }

        return visitedPositions.size
    }

    fun part2(input: List<List<Char>>): Int {
        return 0
    }

}


private val movementDirections = listOf(
    Vector2(0, -1),
    Vector2(1, 0),
    Vector2(0, 1),
    Vector2(-1, 0),
)

private fun turnRight(currentDirection: Vector2): Vector2 {
    var index = movementDirections.indexOf(currentDirection)
    index++
    if (index >= movementDirections.size) {
        index = 0
    }
    return movementDirections[index]
}
