package aoc2022.day24

import utils.Heading
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


private class BlizzardBasin(initialState: List<Blizzard>, val dimensions: Rect) {
    private val blizzardBasinCache = mutableMapOf<Int, List<Blizzard>>()
    private var repetitionTime: Int? = null

    init {
        blizzardBasinCache[0] = initialState
    }

    fun getBlizzardBasinState(time: Int): List<Blizzard> {
        // get key
        val key = if (repetitionTime == null) time else time % repetitionTime!!

        // return state if already calculated
        if (blizzardBasinCache.containsKey(key)) return blizzardBasinCache[key]!!

        // calculate new state if required
        val lastAvailableStateTime = blizzardBasinCache.maxOf { it.key }
        var state = blizzardBasinCache[lastAvailableStateTime]!!
        repeat(time - lastAvailableStateTime) {
            state = state.map { moveBlizzardRespectingBasinBounds(it) }
        }

        // check if there is a repetition
        val duplicateEntry = blizzardBasinCache.entries.find { it.value == state }
        if (duplicateEntry == null) {
            blizzardBasinCache[time] = state
        } else {
            repetitionTime = time - duplicateEntry.key
        }
        return state
    }

    private fun moveBlizzardRespectingBasinBounds(blizzard: Blizzard): Blizzard {
        val newBlizzard = blizzard.move()
        if (newBlizzard.location in dimensions) return newBlizzard
        return when (blizzard.heading) {
            Heading.NORTH -> Blizzard(Vector2(blizzard.location.x, dimensions.yRange.last), Heading.NORTH)
            Heading.SOUTH -> Blizzard(Vector2(blizzard.location.x, dimensions.yRange.first), Heading.SOUTH)
            Heading.WEST -> Blizzard(Vector2(dimensions.xRange.last, blizzard.location.y), Heading.WEST)
            Heading.EAST -> Blizzard(Vector2(dimensions.xRange.first, blizzard.location.y), Heading.EAST)
        }
    }
}

private data class State(
    val currentLocation: Vector2,
    val distanceToEnd: Int,
    val timeUsed: Int = 0
) {

    val arrivedAtEnd: Boolean = distanceToEnd == 0

    fun getPossibleNextStates(blizzardBasin: BlizzardBasin, startLocation: Vector2, endLocation: Vector2): List<State> {

        val nextBlizzardLocations = blizzardBasin.getBlizzardBasinState(timeUsed + 1)
        val nextStates = mutableListOf<State>()

        // if no blizzard moves to current location, remain at current location
        if (nextBlizzardLocations.none { it.location == currentLocation }) {
            nextStates += State(currentLocation, distanceToEnd, timeUsed + 1)
        }

        // move to other locations
        for (heading in Heading.values()) {
            val nextLocation = currentLocation + heading.movementVector
            if ((nextLocation in blizzardBasin.dimensions || nextLocation == startLocation || nextLocation == endLocation) && nextBlizzardLocations.none { it.location == nextLocation }) {
                nextStates += State(
                    nextLocation,
                    nextLocation.manhattanDistanceTo(endLocation).toInt(),
                    timeUsed + 1
                )
            }
        }

        // return found valid states
        return nextStates
    }

}