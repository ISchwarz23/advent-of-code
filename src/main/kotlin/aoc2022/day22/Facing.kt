package aoc2022.day22

import utils.Vector2

enum class Facing(val movementVector: Vector2) {
    RIGHT(Vector2(1, 0)),
    DOWN(Vector2(0, 1)),
    LEFT(Vector2(-1, 0)),
    UP(Vector2(0, -1));

    val value: Int
        get() = all.indexOf(this)

    private fun turn90DegreeRight(): Facing {
        val index = all.indexOf(this)
        val rightIndex = if (index == 3) 0 else index + 1
        return all[rightIndex]
    }

    private fun turn90DegreeLeft(): Facing {
        val index = all.indexOf(this)
        val leftIndex = if (index == 0) 3 else index - 1
        return all[leftIndex]
    }

    fun turn(direction: TurnDirection): Facing {
        return when (direction) {
            TurnDirection.LEFT -> turn90DegreeLeft()
            TurnDirection.RIGHT -> turn90DegreeRight()
            TurnDirection.NONE -> this
        }
    }

    companion object {
        private val all: Array<Facing> = arrayOf(RIGHT, DOWN, LEFT, UP)
    }
}