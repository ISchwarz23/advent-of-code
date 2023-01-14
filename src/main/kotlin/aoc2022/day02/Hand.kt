package aoc2022.day02


enum class Hand(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private val rules by lazy {
        arrayOf(
            Triple(ROCK, PAPER, SCISSORS),
            Triple(PAPER, SCISSORS, ROCK),
            Triple(SCISSORS, ROCK, PAPER),
        )
    }

    fun playAgainst(other: Hand): Result {
        if (this == other) return Result.DRAW

        val rule = rules.find { it.first == this } ?: error("Missing rule for hand $this")
        return if (rule.second == other) Result.LOOSE else Result.WIN
    }

    fun getHandToAchieveResult(result: Result): Hand {
        val rule = rules.find { it.first == this } ?: error("Missing rule for hand $this")
        return when (result) {
            Result.LOOSE -> rule.third
            Result.DRAW -> rule.first
            Result.WIN -> rule.second
        }
    }

    companion object {

        fun from(letter: String): Hand {
            return when (letter) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> error("Unknown Hand letter: $letter")
            }
        }
    }
}