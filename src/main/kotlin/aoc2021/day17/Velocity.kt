package aoc2021.day17


data class Velocity(
    val vX: Int,
    val vY: Int
) {
    fun decrease(): Velocity {
        val newVX = if (vX > 0) vX - 1 else if (vX < 0) vX + 1 else 0
        val newVY = vY - 1
        return Velocity(newVX, newVY)
    }
}