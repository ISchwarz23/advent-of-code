package aoc2023.day08


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

        val map = input.drop(2).map { it.split(" = ") }
            .map { (from, to) -> from to to.drop(1).dropLast(1).split(", ") }
            .associate { (from, to) -> from to Pair(to[0], to[1]) }

        return map.keys.filter { it.last() == 'A' }
            .map { getCycleLength(map, directionSupplier, it) }
            .fold(1L) { value, cycleLength -> value * cycleLength }
    }

    private fun getCycleLength(
        map: Map<String, Pair<String, String>>,
        directionSupplier: DirectionSupplier,
        startLocation: String
    ): Int {
        directionSupplier.reset()

        data class KnownLocation(val location: String, val directionIndex: Int)
        val knownLocations = hashSetOf<KnownLocation>()

        var currentLocation = KnownLocation(startLocation, directionSupplier.getCurrentIndex())
        while (!knownLocations.contains(currentLocation)) {
            knownLocations += currentLocation
            val directionOptions = map[currentLocation.location] ?: throw RuntimeException("Corrupt Map")
            val current = if(directionSupplier.getNext() == Direction.LEFT) directionOptions.first else directionOptions.second
            currentLocation = KnownLocation(current, directionSupplier.getCurrentIndex())
        }

        return knownLocations.size - 1
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

    fun getCurrentIndex(): Int {
        return index
    }

}
