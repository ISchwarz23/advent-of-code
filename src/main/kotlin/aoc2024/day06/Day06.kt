package aoc2024.day06

import utils.Vector2

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
        val startPosition = input.mapIndexed { y, fields -> Vector2(fields.indexOf('^'), y) }.find { it.x >= 0 }
            ?: throw RuntimeException("No start symbol in input")

        var counter = 0
        input.forEachIndexed { y, fields ->
            fields.forEachIndexed { x, field ->
                if (field == '.') {
                    val copy = input.map { it.toMutableList() }
                    copy[y][x] = '#'
                    if (hasLoop(copy, startPosition)) {
                        counter++
                    }
                }
            }
        }
        return counter
    }

    private fun hasLoop(
        field: List<List<Char>>,
        startPosition: Vector2,
        mov: Vector2 = movementDirections[0],
        visitedF: MutableSet<PositionState> = mutableSetOf()
    ): Boolean {

        var position = startPosition
        var movement = mov
        val visitedFields = visitedF.toList().toMutableList()
        while (position.y in field.indices && position.x in field[0].indices) {
            if (visitedFields.contains(PositionState(position, movement))) {
                return true
            }
            visitedFields += PositionState(position, movement)

            position += movement
            if (position.y in field.indices && position.x in field[0].indices && field[position.y.toInt()][position.x.toInt()] == '#') {
                position -= movement
                movement = turnRight(movement)
            }
        }

        return false
    }

}


private data class PositionState(val position: Vector2, val direction: Vector2)

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
