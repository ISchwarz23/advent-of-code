package aoc2022

object Day02 {

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ") }.map { Pair(toHand(it[0]), toHand(it[1])) }
            .sumOf { getPointsDependingOnHand(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ") }.map { Pair(toHand(it[0]), toResult(it[1])) }
            .sumOf { getPointsDependingOnResult(it) }
    }

    private fun getPointsDependingOnHand(match: Pair<Hand, Hand>): Int {
        var points = 0;
        points += when (match.second) {
            Hand.ROCK -> 1
            Hand.PAPER -> 2
            Hand.SCISSORS -> 3
        }
        // loose: 0; draw: 3; win: 6
        if (match.first == match.second) {
            points += 3
        } else {
            val rule =
                RULES.find { it.me == match.second } ?: throw RuntimeException("No rule found for ${match.second}")
            if (match.first == rule.winsAgainst) {
                points += 6
            }
        }
        return points
    }

    private fun getPointsDependingOnResult(match: Pair<Hand, Result>): Int {
        var points = 0;
        points += when (match.second) {
            Result.LOOSE -> 0
            Result.DRAW -> 3
            Result.WIN -> 6
        }

        val rule = RULES.find { it.me == match.first } ?: throw RuntimeException("No rule found for ${match.second}")
        val handToPlay = when (match.second) {
            Result.LOOSE -> rule.winsAgainst
            Result.DRAW -> rule.me
            Result.WIN -> rule.loosesAgainst
        }
        points += when (handToPlay) {
            Hand.ROCK -> 1
            Hand.PAPER -> 2
            Hand.SCISSORS -> 3
        }
        return points
    }
}

private fun toHand(letter: String): Hand {
    return when (letter) {
        "A", "X" -> Hand.ROCK
        "B", "Y" -> Hand.PAPER
        "C", "Z" -> Hand.SCISSORS
        else -> {
            throw RuntimeException("Unknown Hand letter: $letter")
        }
    }
}

private fun toResult(letter: String): Result {
    return when (letter) {
        "X" -> Result.LOOSE
        "Y" -> Result.DRAW
        "Z" -> Result.WIN
        else -> {
            throw RuntimeException("Unknown Result letter: $letter")
        }
    }
}

enum class Hand {
    ROCK,
    PAPER,
    SCISSORS;
}

enum class Result {
    LOOSE,
    DRAW,
    WIN
}

data class Rule(val me: Hand, val loosesAgainst: Hand, val winsAgainst: Hand)

val RULES = arrayOf(
    Rule(Hand.ROCK, Hand.PAPER, Hand.SCISSORS),
    Rule(Hand.PAPER, Hand.SCISSORS, Hand.ROCK),
    Rule(Hand.SCISSORS, Hand.ROCK, Hand.PAPER)
)
