package aoc2023.day10

import utils.Vector2

/**
 * My solution for day 10 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/10">AoC page</a>.
 */
object Day10 {

    val directionRight = Vector2(1, 0)
    val directionUp = Vector2(0, -1)
    val directionLeft = Vector2(-1, 0)
    val directionDown = Vector2(0, 1)

    enum class Movement(private val direction: Vector2, private val fieldsThatCanBeEntered: List<Char>, private val resultingDirection: Map<Char, Vector2>) {
        RIGHT(directionRight, listOf('-', '7', 'J'), mapOf('7' to directionDown, 'J' to directionUp)),
        UP(directionUp, listOf('|', 'F', '7'), mapOf('F' to directionRight, '7' to directionLeft)),
        LEFT(directionLeft, listOf('-', 'F', 'L'), mapOf('F' to directionDown, 'L' to directionUp)),
        DOWN(directionDown, listOf('|', 'L', 'J'), mapOf('L' to directionRight, 'J' to directionLeft));

        fun canDoMove(map: List<List<Char>>, startTile: Vector2): Boolean {
            val end = startTile + direction
            return map[end.y.toInt()][end.x.toInt()] in fieldsThatCanBeEntered
        }

        fun doMove(currentTile: Vector2): Vector2 {
            return currentTile + direction
        }

        fun getNewMovement(currentPipe: Char): Movement {
            val direction = resultingDirection[currentPipe] ?: direction
            return Movement.fromDirection(direction)!!
        }

        companion object {

            fun fromDirection(direction: Vector2): Movement? {
                return Movement.values().firstOrNull { it.direction == direction }
            }

        }
    }

    fun part1(pipeTiles: List<List<Char>>): Int {
        val startTile = pipeTiles.asSequence()
            .flatMapIndexed { y, row ->
                row.mapIndexed { x, pipe -> pipe to Vector2(x, y) }
            }
            .first { (pipe, _) -> pipe == 'S' }.second

        var move = Movement.values().first { it.canDoMove(pipeTiles, startTile) }
        var currentTile = startTile
        var noOfMoves = 0
        do {
            currentTile = move.doMove(currentTile)
            noOfMoves++

            val currentPipe = pipeTiles[currentTile.y.toInt()][currentTile.x.toInt()]
            move = move.getNewMovement(currentPipe)
        } while (currentPipe != 'S')

        return noOfMoves / 2
    }

    fun part2(input: List<List<Char>>): Int {
        return 0
    }

}
