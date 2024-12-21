package aoc2023.day08

import utils.MathUtils


/**
 * My solution for day 8 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/8">AoC page</a>.
 */
object Day08 {

    fun part1(input: List<String>): Int {
        val directions = input[0].toCharArray().map { if(it == 'L') Direction.LEFT else Direction.RIGHT }
        val directionSupplier = DirectionSupplier(directions)

        val map = input.drop(2).map { it.split(" = ") }
            .map { (from, to) -> from to to.drop(1).dropLast(1).split(", ") }
            .associate { (from, to) -> from to Pair(to[0], to[1]) }

        var currentLocation = "AAA"
        val targetLocation = "ZZZ"
        var numberOfSteps = 0
        do {
            val directionOptions = map[currentLocation] ?: throw RuntimeException("Corrupt Map")
            currentLocation = if(directionSupplier.getNext() == Direction.LEFT) directionOptions.first else directionOptions.second
            numberOfSteps++
        } while (currentLocation != targetLocation)

        return numberOfSteps
    }

    fun part2(input: List<String>): Long {
        val directions = input[0].toCharArray().map { if(it == 'L') Direction.LEFT else Direction.RIGHT }
        val directionSupplier = DirectionSupplier(directions)

        val map = input.drop(2).map { "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex().matchEntire(it)!!.groupValues }
            .associate { (_, from, toLeft, toRight) -> from to Pair(toLeft, toRight) }

        return map.keys.filter { it.last() == 'A' }
            .map { getCycleLength(map, directionSupplier, it).toLong() }
            .fold(1L) { value, cycleLength -> MathUtils.leastCommonMultiple(value, cycleLength) }
    }

    private fun getCycleLength(
        map: Map<String, Pair<String, String>>,
        directionSupplier: DirectionSupplier,
        startLocation: String
    ): Int {
        directionSupplier.reset()

        var cycleLength = 0

        var currentLocation = startLocation
        while (!currentLocation.endsWith("Z")) {
            cycleLength++

            val directions = map[currentLocation] ?: throw RuntimeException("Corrupt Map")
            currentLocation = if(directionSupplier.getNext() == Direction.LEFT) directions.first else directions.second
        }

        return cycleLength
    }

}

private enum class Direction {
    LEFT,
    RIGHT
}

private class DirectionSupplier(private val directions: List<Direction>) {

    private var index: Int = 0

    fun getNext(): Direction {
        if (index >= directions.size) {
            index = 0
        }
        val direction = directions[index]
        index++
        return direction
    }

    fun reset() {
        this.index = 0
    }

}
