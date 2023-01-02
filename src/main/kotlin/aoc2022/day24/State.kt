package aoc2022.day24

import utils.Heading
import utils.Vector2


data class State(
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