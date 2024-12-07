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

    fun part2(input: List<List<Char>>): Int { // TODO
        val startPosition = input.mapIndexed { y, fields -> Vector2(fields.indexOf('^'), y) }.find { it.x >= 0 }
            ?: throw RuntimeException("No start symbol in input")
        val obstacles = input.flatMapIndexed{ y, fields -> fields.indexesOf('#').map { x -> Vector2(x, y) } }
        var position = startPosition
        var movementDirection = movementDirections[0]

        val visitedPositions = mutableSetOf<PositionState>()
        val obstaclesForCircles = mutableSetOf<Vector2>()

        while (position.y in input.indices && position.x in input[0].indices) {
            visitedPositions += PositionState(position, movementDirection)

            // check potential block
            val obstacle = position + movementDirection
            val directionAfterBlock = turnRight(movementDirection)
            var positionAfterBlock = position + directionAfterBlock
            while (positionAfterBlock.y in input.indices && positionAfterBlock.x in input[0].indices
                && input[positionAfterBlock.y.toInt()][positionAfterBlock.x.toInt()] != '#'
            ) {
                if (visitedPositions.contains(PositionState(positionAfterBlock, directionAfterBlock))) {
                    obstaclesForCircles += obstacle
                    break
                }
                positionAfterBlock += directionAfterBlock
            }

            // keep moving
            position += movementDirection
            if (position.y in input.indices && position.x in input[0].indices && input[position.y.toInt()][position.x.toInt()] == '#') {
                position -= movementDirection
                movementDirection = turnRight(movementDirection)
            }
        }

        obstaclesForCircles -= startPosition
        obstaclesForCircles -= obstacles.toSet()

        // print(input, obstaclesForCircles)
        return obstaclesForCircles.size
    }

}

private fun print(field: List<List<Char>>, potentialObstacles: Set<Vector2>) {
    field.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (potentialObstacles.contains(Vector2(x, y))) {
                print('O')
            } else {
                print(field[y][x])
            }
        }
        println()
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
