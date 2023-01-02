package aoc2022.day24

import utils.Heading
import utils.Rect
import utils.Vector2

class BlizzardBasin(initialState: List<Blizzard>, val dimensions: Rect) {
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