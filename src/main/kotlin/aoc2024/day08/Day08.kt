package aoc2024.day08

import utils.Rect
import utils.Vector2

/**
 * My solution for day 8 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/8">AoC page</a>.
 */
object Day08 {

    fun part1(input: List<List<Char>>): Int {
        val areaBounds = Rect(input[0].indices, input.indices)

        return input.asSequence()
            .flatMapIndexed { y: Int, line: List<Char> -> line.mapIndexed { x: Int, frequency: Char -> Beacon(frequency, Vector2(x, y)) } }
            .filter { it.frequency != '.' }
            .groupBy { it.frequency }
            .flatMap { calculateAntiNodes(it.value.map { beacon -> beacon.location }) }
            .filter { it in areaBounds }
            .distinct()
            .count()
    }

    fun part2(input: List<List<Char>>): Int {
        val areaBounds = Rect(input[0].indices, input.indices)

        return input.asSequence()
            .flatMapIndexed { y: Int, line: List<Char> -> line.mapIndexed { x: Int, frequency: Char -> Beacon(frequency, Vector2(x, y)) } }
            .filter { it.frequency != '.' }
            .groupBy { it.frequency }
            .flatMap { calculateAntiNodes(it.value.map { beacon -> beacon.location }, areaBounds) }
            .distinct()
            .count()
    }

}


private fun calculateAntiNodes(nodeLocations: List<Vector2>, bounds: Rect? = null): List<Vector2> {
    val antiNodeLocations = mutableListOf<Vector2>()
    for (nodeLocation1 in nodeLocations) {
        for (nodeLocation2 in nodeLocations) {
            val distance = nodeLocation1 - nodeLocation2
            if (distance.abs().linearMagnitude() > 0) {
                if(bounds == null) {
                    // only one antinode
                    antiNodeLocations += nodeLocation1 + distance
                } else {
                    // repeat antinodes inside bounds
                    var antiNodeLocation = nodeLocation1
                    while (antiNodeLocation in bounds) {
                        antiNodeLocations += antiNodeLocation
                        antiNodeLocation += distance
                    }
                }
            }
        }
    }
    return antiNodeLocations
}

data class Beacon(val frequency: Char, val location: Vector2)
