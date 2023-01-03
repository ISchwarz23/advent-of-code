package aoc2021.day13


data class Dot(val x: Int, val y: Int) {

    fun copyAndMoveUp(upwardMovement: Int): Dot {
        return Dot(x, y - upwardMovement)
    }

    fun copyAndMoveLeft(leftMovement: Int): Dot {
        return Dot(x - leftMovement, y)
    }

}