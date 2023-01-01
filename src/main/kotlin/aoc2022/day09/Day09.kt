package aoc2022.day09

import kotlin.math.absoluteValue

object Day09 {

    fun part1(movements: List<Movement>): Int {
        return moveRope(movements, 2)
    }

    fun part2(movements: List<Movement>): Int {
        return moveRope(movements, 10)
    }
}

private fun moveRope(movements: List<Movement>, ropeLength: Int): Int {
    val positionsVisitedByTail = mutableSetOf<Position>()
    val ropeSectionPositions = MutableList(ropeLength) { Position(0, 0) }

    for (movement in movements) {
        repeat(movement.numberOfMoves) {
            ropeSectionPositions[0] = ropeSectionPositions[0].move(movement.direction)
            for (i in 1 until ropeSectionPositions.size) {
                ropeSectionPositions[i] = moveTail(ropeSectionPositions[i - 1], ropeSectionPositions[i])
            }
            positionsVisitedByTail += ropeSectionPositions.last()
        }
    }
    return positionsVisitedByTail.size
}

private fun moveTail(headPosition: Position, tailPosition: Position): Position {
    val diffX = (tailPosition.x - headPosition.x).absoluteValue
    val diffY = (tailPosition.y - headPosition.y).absoluteValue

    return if (diffX <= 1 && diffY <= 1) {
        tailPosition
    } else {
        var moveX = diffX - 1
        var moveY = diffY - 1
        if (diffX > diffY) {
            ++moveY
        } else if(diffY > diffX) {
            ++moveX
        }
        moveY = if (tailPosition.y > headPosition.y) moveY * -1 else moveY
        moveX = if (tailPosition.x > headPosition.x) moveX * -1 else moveX
        Position(tailPosition.x + moveX, tailPosition.y + moveY)
    }
}

data class Movement(
    val direction: Direction,
    val numberOfMoves: Int
)

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        fun fromString(input: String): Direction {
            return when (input) {
                "U" -> UP
                "D" -> DOWN
                "L" -> LEFT
                "R" -> RIGHT
                else -> throw RuntimeException("Unknown direction '$input'")
            }
        }
    }
}

private data class Position(
    val x: Int,
    val y: Int
) {

    fun move(direction: Direction): Position {
        return when (direction) {
            Direction.UP -> Position(x, y + 1)
            Direction.RIGHT -> Position(x + 1, y)
            Direction.DOWN -> Position(x, y - 1)
            Direction.LEFT -> Position(x - 1, y)
        }
    }

}
