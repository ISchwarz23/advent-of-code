package aoc2022.day24

import utils.Rect
import utils.Vector2
import java.util.*

/**
 * My solution for day 24 of Advent of Code 2022.
 * The puzzle can be found at the <a href="https://adventofcode.com/2022/day/24">AoC page</a>.
 */
object Day24 {

    fun part1(basinDimensions: Rect, blizzards: List<Blizzard>): Int {

        val startLocation = Vector2(basinDimensions.xRange.first, basinDimensions.yRange.first - 1)
        val endLocation = Vector2(basinDimensions.xRange.last, basinDimensions.yRange.last + 1)
        val blizzardBasin = BlizzardBasin(blizzards, basinDimensions)

        return getFastestPath(blizzardBasin, startLocation, endLocation)
    }

    fun part2(basinDimensions: Rect, blizzards: List<Blizzard>): Int {
        val startLocation = Vector2(basinDimensions.xRange.first, basinDimensions.yRange.first - 1)
        val endLocation = Vector2(basinDimensions.xRange.last, basinDimensions.yRange.last + 1)
        val waypoints = listOf(startLocation, endLocation, startLocation, endLocation)
        val blizzardBasin = BlizzardBasin(blizzards, basinDimensions)

        var timeUsed = 0
        for ((start, end) in waypoints.windowed(2)) {
            timeUsed = getFastestPath(blizzardBasin, start, end, timeUsed)
        }
        return timeUsed
    }

    private fun getFastestPath(
        blizzardBasin: BlizzardBasin,
        startLocation: Vector2,
        endLocation: Vector2,
        startTime: Int = 0
    ): Int {
        val states = PriorityQueue<State> { first, second ->
            (first.timeUsed + first.distanceToEnd) - (second.timeUsed + second.distanceToEnd)
        }
        states += State(startLocation, startLocation.manhattanDistanceTo(endLocation).toInt(), startTime)
        val knownStates = mutableListOf<State>()

        var result: State? = null
        while (states.isNotEmpty() && result == null) {
            val currentState = states.poll()

            if (currentState.arrivedAtEnd) {
                result = currentState
            } else {
                currentState.getPossibleNextStates(blizzardBasin, startLocation, endLocation)
                    .filter { it !in knownStates }
                    .forEach {
                        states += it
                        knownStates += it
                    }
            }
        }
        return result?.timeUsed ?: -1
    }
}
