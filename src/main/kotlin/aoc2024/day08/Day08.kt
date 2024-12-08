package aoc2024.day08

import utils.Vector2

/**
 * My solution for day 8 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/8">AoC page</a>.
 */
object Day08 {

    fun part1(input: List<List<Char>>): Int {
        return input.flatMapIndexed { y: Int, line: List<Char> -> line.mapIndexed { x: Int, frequency: Char -> Beacon(frequency, Vector2(x, y)) } }
            .filter { it.frequency != '.' }
            .groupBy { it.frequency }
            .flatMap { calculateAntinodes(it.value.map { beacon -> beacon.location }) }
            .filter { it.y in input.indices }
            .filter { it.x in input[0].indices }
            .distinct()
            .count()
    }

    fun part2(input: List<List<Char>>): Int {
        return 0
    }

}

private fun calculateAntinodes(nodeLocations: List<Vector2>): List<Vector2> {
    val antiNodeLocations = mutableListOf<Vector2>()
    for (nodeLocation1 in nodeLocations) {
        for (nodeLocation2 in nodeLocations) {
            val distance = nodeLocation1 - nodeLocation2
            if (distance.abs().linearMagnitude() > 0) {
                antiNodeLocations += nodeLocation1 + distance
            }
        }
    }
    return antiNodeLocations
}

data class Beacon(val frequency: Char, val location: Vector2)
