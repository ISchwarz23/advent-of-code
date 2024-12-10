package aoc2024.day10

import utils.Rect
import utils.Vector2

/**
 * My solution for day 10 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/10">AoC page</a>.
 */
object Day10 {

    fun part1(map: List<List<Int>>): Int {
        return map.flatMapIndexed { y, row -> row.mapIndexed { x, height -> MapPoint(Vector2(x, y), height) } }
            .filter { it.height == 0 }
            .map { it.position }
            .sumOf { numberOfTrailHeads(map, it) }
    }

    fun part2(input: List<List<Int>>): Int {
        return 0
    }

}

private class MapPoint(val position: Vector2, val height: Int)


private fun numberOfTrailHeads(map: List<List<Int>>, startPoint: Vector2, directionsToCheck: List<Vector2> = listOf(
    Vector2(1, 0),
    Vector2(0, 1),
    Vector2(-1, 0),
    Vector2(0, -1),
)): Int {
    val mapBounds = Rect(map[0].indices, map.indices)
    val trailHeads = mutableSetOf<Vector2>()
    val currentLocations = mutableListOf(startPoint)

    while (currentLocations.isNotEmpty()) {
        val currentLocation = currentLocations.removeFirst()
        if(getHeightAtLocation(map, currentLocation) == 9) {
            trailHeads += currentLocation
        }

        directionsToCheck.map { it + currentLocation }
            .filter { it in mapBounds }
            .filter { getHeightAtLocation(map, it) - getHeightAtLocation(map, currentLocation) == 1 }
            .forEach { currentLocations += it }
    }

    return trailHeads.size
}

private fun getHeightAtLocation(map: List<List<Int>>, location: Vector2) = map[location.y.toInt()][location.x.toInt()]
