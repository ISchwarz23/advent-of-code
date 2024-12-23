package aoc2023.day11

import utils.Vector2

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
            row.mapIndexed { x, c -> if (c == '#') Vector2(x, y) else null }
        }.filterNotNull()

        val columnsWithoutGalaxies = MutableList(input[0].size) { it }
        val rowsWithoutGalaxies = MutableList(input.size) { it }
        rawGalaxyCoordinates.forEach {
            columnsWithoutGalaxies -= it.x.toInt()
            rowsWithoutGalaxies -= it.y.toInt()
        }

        val galaxyCoordinates = rawGalaxyCoordinates.map { rawGalaxyCoordinate ->
            Vector2(
                rawGalaxyCoordinate.x + (expansionFactor - 1) * columnsWithoutGalaxies.count { it < rawGalaxyCoordinate.x },
                rawGalaxyCoordinate.y + (expansionFactor - 1) * rowsWithoutGalaxies.count { it < rawGalaxyCoordinate.y }
            )
        }

        var distanceSum = 0L
        galaxyCoordinates.subList(0, galaxyCoordinates.size - 1).forEachIndexed { firstGalaxyIndex, firstGalaxy ->
            galaxyCoordinates.subList(firstGalaxyIndex + 1, galaxyCoordinates.size).forEach { secondsGalaxy ->
                distanceSum += firstGalaxy.manhattanDistanceTo(secondsGalaxy)
            }
        }
        return distanceSum
    }

}
