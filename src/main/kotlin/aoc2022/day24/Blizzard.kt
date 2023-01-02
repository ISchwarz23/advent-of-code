package aoc2022.day24

import utils.Vector2

data class Blizzard(val location: Vector2, val heading: Heading) {

    fun move(): Blizzard {
        return Blizzard(location + heading.movementVector, heading)
    }

}