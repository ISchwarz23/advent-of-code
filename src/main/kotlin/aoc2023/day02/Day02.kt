package aoc2023.day02

import kotlin.math.max

/**
 * My solution for day 2 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/2">AoC page</a>.
 */
object Day02 {

    fun part1(input: List<String>): Int {
        return input.map { parseGame(it) }
            .filter { it.isFeasibleWithGivenNumberOfCubes(12, 13, 14) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.map { parseGame(it) }
            .map { it.getMinimumRequiredCubes() }
            .map { it.noOfRedCubes * it.noOfGreenCubes * it.noOfBlueCubes }
            .sumOf { it }
    }

}

private fun parseGame(line: String): Game {
    val (rawId, rawSets) = line.split(": ")
    val id = rawId.substring(5).toInt()
    val sets = rawSets.split("; ").map { parseSet(it) }
    return Game(id, sets)
}

fun parseSet(rawSet: String): GameSet {
    val cubes = rawSet.split(", ")
        .map { it.split(" ") }
        .associate { it[1] to it[0].toInt() }

    return GameSet(
        cubes.getOrDefault("red", 0),
        cubes.getOrDefault("green", 0),
        cubes.getOrDefault("blue", 0)
    )
}

data class Game(
    val id: Int,
    val sets: List<GameSet>
) {

    fun isFeasibleWithGivenNumberOfCubes(noOfRedCubes: Int, noOfGreenCubes: Int, noOfBlueCubes: Int) =
        sets.all { it.isFeasibleWithGivenNumberOfCubes(noOfRedCubes, noOfGreenCubes, noOfBlueCubes) }

    fun getMinimumRequiredCubes(): GameSet {
        var noOfRedCubes: Int = 0
        var noOfGreenCubes: Int = 0
        var noOfBlueCubes: Int = 0

        for (set in sets) {
            noOfRedCubes = max(noOfRedCubes, set.noOfRedCubes)
            noOfGreenCubes = max(noOfGreenCubes, set.noOfGreenCubes)
            noOfBlueCubes = max(noOfBlueCubes, set.noOfBlueCubes)
        }

        return GameSet(noOfRedCubes, noOfGreenCubes, noOfBlueCubes)
    }

}

data class GameSet(
    val noOfRedCubes: Int,
    val noOfGreenCubes: Int,
    val noOfBlueCubes: Int,
) {

    fun isFeasibleWithGivenNumberOfCubes(noOfRedCubes: Int, noOfGreenCubes: Int, noOfBlueCubes: Int) =
        this.noOfRedCubes <= noOfRedCubes && this.noOfGreenCubes <= noOfGreenCubes && this.noOfBlueCubes <= noOfBlueCubes

}