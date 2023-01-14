package aoc2022.day02


enum class Result(val points: Int) {
    LOOSE(0),
    DRAW(3),
    WIN(6);

    val opposite: Result
        get() = when (this) {
            LOOSE -> WIN
            DRAW -> DRAW
            WIN -> LOOSE
        }

    companion object {

        fun from(letter: String): Result {
            return when (letter) {
                "X" -> LOOSE
                "Y" -> DRAW
                "Z" -> WIN
                else -> error("Unknown Result letter: $letter")
            }
        }
    }
}