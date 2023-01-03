package aoc2021.day17

data class TargetArea(
    val xStart: Int,
    val xEnd: Int,
    val yStart: Int,
    val yEnd: Int
) {

    fun contains(position: Position): Boolean {
        if (position.x < xStart || position.x > xEnd) return false
        if (position.y < yStart || position.y > yEnd) return false
        return true
    }
}