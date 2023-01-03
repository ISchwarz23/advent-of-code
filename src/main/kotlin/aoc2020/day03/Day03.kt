package aoc2020.day03

import utils.Rect
import utils.Vector2

/**
 * My solution for day 3 of Advent of Code 2020.
 * The puzzle can be found at the <a href="https://adventofcode.com/2020/day/3">AoC page</a>.
 */
object Day03 {

    fun part1(treeLocations: List<Vector2>): Long {
        return getNumberOfTrees(treeLocations, Vector2(3, 1))
    }

    fun part2(treeLocations: List<Vector2>): Long {
        return listOf(
            Vector2(1, 1),
            Vector2(3, 1),
            Vector2(5, 1),
            Vector2(7, 1),
            Vector2(1, 2)
        ).map { getNumberOfTrees(treeLocations, it) }.reduce(Long::times)
    }

    private fun getNumberOfTrees(
        input: List<Vector2>,
        movementVector: Vector2
    ): Long {
        var currentPosition = Vector2(0, 0)
        val bounds = Rect(0..input.maxOf { it.x }, 0..input.maxOf { it.y })
        val resetVector = Vector2(bounds.width * -1, 0)

        var treeCounter = 0L
        while (currentPosition.y in bounds.yRange) {
            currentPosition += movementVector
            if (currentPosition.x !in bounds.xRange) currentPosition += resetVector
            if (currentPosition in input) treeCounter++
        }
        return treeCounter
    }
}
