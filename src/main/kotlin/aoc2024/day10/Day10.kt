package aoc2024.day10

import utils.Rect
import utils.Vector2

/**
 * My solution for day 10 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/10">AoC page</a>.
 */
object Day10 {

    fun part1(input: List<List<Int>>): Int {
        return input.flatMapIndexed { y, row -> row.mapIndexed { x, height -> MapPoint(Vector2(x, y), height) } }
            .filter { it.height == 0 }
            .map { it.position }
            .sumOf { getReachableTrailHeads(Map(input), it).distinct().size }
    }

    fun part2(input: List<List<Int>>): Int {
        return input.flatMapIndexed { y, row -> row.mapIndexed { x, height -> MapPoint(Vector2(x, y), height) } }
            .filter { it.height == 0 }
            .map { it.position }
            .sumOf { getReachableTrailHeads(Map(input), it).size }
    }

}

private class MapPoint(val position: Vector2, val height: Int)

private class Map(private val map: List<List<Int>>) {

    val bounds = Rect(map[0].indices, map.indices)

    fun getHeight(location: Vector2): Int = map[location.y.toInt()][location.x.toInt()]

}

private val VALID_DIRECTIONS = listOf(
    Vector2(1, 0),  // right
    Vector2(0, 1),  // down
    Vector2(-1, 0), // left
    Vector2(0, -1)  // up
)

private fun getReachableTrailHeads(map: Map, startPoint: Vector2): List<Vector2> {

    val trailHeads = mutableListOf<Vector2>()
    val currentLocations = mutableListOf(startPoint)

    while (currentLocations.isNotEmpty()) {
        val currentLocation = currentLocations.removeFirst()
        if(map.getHeight(currentLocation) == 9) {
            trailHeads += currentLocation
        }

        VALID_DIRECTIONS.map { direction -> currentLocation + direction }
            .filter { nextLocation -> nextLocation in map.bounds }
            .filter { nextLocation -> map.getHeight(nextLocation) - map.getHeight(currentLocation) == 1 }
            .forEach { nextLocation -> currentLocations += nextLocation }
    }

    return trailHeads
}

