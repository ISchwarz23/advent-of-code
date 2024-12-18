package aoc2024.day18

import utils.Rect
import utils.Vector2
import java.util.PriorityQueue

/**
 * My solution for day 18 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/18">AoC page</a>.
 */
object Day18 {

    fun part1(fallingBytes: List<Vector2>, areaBounds: Rect, noOfBytesToRespect: Int): Int {
        val corruptedFields = fallingBytes.take(noOfBytesToRespect)

        val result = solveLabyrinth(areaBounds, corruptedFields)

        print(areaBounds, corruptedFields, result?.history ?: emptyList())
        return result?.history?.size ?: -1
    }

    fun part2(fallingBytes: List<Vector2>, areaBounds: Rect, initialNoOfBytesToRespect: Int = 1): String {

        var checkInterval = initialNoOfBytesToRespect
        var noOfBytesToRespect = initialNoOfBytesToRespect

        do {
            checkInterval /= 2
            do {
                noOfBytesToRespect += checkInterval
                val corruptedFields = fallingBytes.take(noOfBytesToRespect)
                val solution = solveLabyrinth(areaBounds, corruptedFields)
            } while (solution != null)
            noOfBytesToRespect -= checkInterval
        } while (checkInterval != 1)

        val result = fallingBytes[noOfBytesToRespect]
        return "${result.x},${result.y}"
    }

    private fun solveLabyrinth(
        areaBounds: Rect,
        corruptedFields: List<Vector2>
    ): LabyrinthState? {
        val startPosition = Vector2(areaBounds.xRange.first, areaBounds.yRange.first)
        val endPosition = Vector2(areaBounds.xRange.last, areaBounds.yRange.last)

        val visitedFields = mutableSetOf<Vector2>()
        val states = PriorityQueue<LabyrinthState> { state1, state2 ->
            (state1.position.manhattanDistanceTo(endPosition).toInt() + state1.history.size) -
                    (state2.position.manhattanDistanceTo(endPosition).toInt() + state2.history.size)
        }

        var currentState: LabyrinthState? = LabyrinthState(startPosition)
        while (currentState != null && currentState.position != endPosition) {
            visitedFields += currentState.position

            currentState.getPossibleNextStates()
                .filter { it.position in areaBounds }
                .filterNot { it.position in visitedFields }
                .filterNot { it.position in corruptedFields }
                .forEach {
                    visitedFields += it.position
                    states += it
                }

            currentState = states.poll()
        }
        return currentState
    }

}

private fun print(area: Rect, corruptedFields: List<Vector2>, visitedFields: List<Vector2>) {
    for (y in area.yRange) {
        for (x in area.xRange) {
            val field = Vector2(x, y)
            when (field) {
                in visitedFields -> print("O")
                in corruptedFields -> print("#")
                else -> print(".")
            }
        }
        println()
    }

}

private data class LabyrinthState(val position: Vector2, val history: List<Vector2> = emptyList()) {

    fun getPossibleNextStates(): List<LabyrinthState> = listOf(
        LabyrinthState(position + Vector2(0, -1), history + position),
        LabyrinthState(position + Vector2(-1, 0), history + position),
        LabyrinthState(position + Vector2(0, 1), history + position),
        LabyrinthState(position + Vector2(1, 0), history + position)
    )
}
