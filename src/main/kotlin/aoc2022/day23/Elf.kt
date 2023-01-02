package aoc2022.day23

import utils.Rect
import utils.Vector2

data class Elf(var position: Vector2, var plannedPosition: Vector2? = null) {

    val neighborhoodArea: Rect
        get() = Rect(position.x - 1..position.x + 1, position.y - 1..position.y + 1)

    fun planMove(direction: Heading) {
        plannedPosition = position + direction.movementVector
    }

    fun moveToPlannedPosition() {
        if (plannedPosition == null) return
        position = plannedPosition!!
        plannedPosition = null
    }

    fun cancelPlannedMove() {
        plannedPosition = null
    }

}