package utils


enum class Heading(val movementVector: Vector2) {
    NORTH(Vector2(0, -1)),
    EAST(Vector2(1, 0)),
    SOUTH(Vector2(0, 1)),
    WEST(Vector2(-1, 0));

    fun turn90DegreeRight(): Heading {
        val index = Heading.values().indexOf(this)
        val rightIndex = if (index == 3) 0 else index + 1
        return Heading.values()[rightIndex]
    }

    fun turn90DegreeLeft(): Heading {
        val index = Heading.values().indexOf(this)
        val leftIndex = if (index == 0) 3 else index - 1
        return Heading.values()[leftIndex]
    }

    fun turnToOppositeDirection(): Heading {
        return this.turn90DegreeRight().turn90DegreeRight()
    }

}