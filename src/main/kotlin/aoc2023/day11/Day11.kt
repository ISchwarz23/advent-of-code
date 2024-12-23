package aoc2023.day11

import utils.Vector2
import utils.combinationsAsSequence

/**
 * My solution for day 11 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/11">AoC page</a>.
 */
object Day11 {

    fun part1(input: List<List<Char>>): Long {
        return calculateDistances(input, 2)
    }

    fun part2(input: List<List<Char>>, expansionFactor: Long): Long {
        return calculateDistances(input, expansionFactor)
    }

    private fun calculateDistances(input: List<List<Char>>, expansionFactor: Long): Long {
        val rawGalaxyCoordinates = input.flatMapIndexed { y: Int, row: List<Char> ->
            row.mapIndexedNotNull { x, c -> Vector2(x, y).takeIf { c == '#' } }
        }

        val columnsWithoutGalaxies = MutableList(input[0].size) { it.toLong() }
        val rowsWithoutGalaxies = MutableList(input.size) { it.toLong() }
        rawGalaxyCoordinates.forEach {
            columnsWithoutGalaxies -= it.x
            rowsWithoutGalaxies -= it.y
        }

        val galaxyCoordinates = rawGalaxyCoordinates.map { rawGalaxyCoordinate ->
            Vector2(
                rawGalaxyCoordinate.x + (expansionFactor - 1) * columnsWithoutGalaxies.count { it < rawGalaxyCoordinate.x },
                rawGalaxyCoordinate.y + (expansionFactor - 1) * rowsWithoutGalaxies.count { it < rawGalaxyCoordinate.y }
            )
        }

        return galaxyCoordinates.combinationsAsSequence(2).sumOf { (firstGalaxy, secondsGalaxy) ->
            firstGalaxy.manhattanDistanceTo(secondsGalaxy)
        }
    }

}
