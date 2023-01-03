package aoc2021.day17

data class Position(
    val x: Int,
    val y: Int
) {
    operator fun plus(velocity: Velocity): Position {
        return Position(x + velocity.vX, y + velocity.vY)
    }
}