package aoc2022.day23

import utils.Heading
import utils.Rect

/**
 * My solution for day 23 of Advent of Code 2022.
 * The puzzle can be found at the <a href="https://adventofcode.com/2022/day/23">AoC page</a>.
 */
object Day23 {

    fun part1(elves: List<Elf>): Long {
        repeat(10) {
            executeRound(elves, MoveDirectionProvider.getForRound(it))
        }
        return countEmptySpaces(elves)
    }

    fun part2(elves: List<Elf>): Int {
        var roundCounter = 0
        var previousElves: List<Elf>
        do {
            previousElves = elves.map { it.copy() }
            executeRound(elves, MoveDirectionProvider.getForRound(roundCounter))
            roundCounter++
        } while (previousElves != elves)
        return roundCounter
    }
}

private fun executeRound(elves: List<Elf>, moveDirections: List<Heading>) {
    // FIRST HALF OF ROUND
    for (elf in elves) {
        val neighbours = findNeighbours(elf, elves)

        // continue with next elf, if no neighbours at all
        if (neighbours.isEmpty()) continue

        // evaluate direction to move
        for (direction in moveDirections) {
            val neighborsInDirection = neighbours.getNeighborsInDirection(direction)
            if (neighborsInDirection.isEmpty()) {
                elf.planMove(direction)
                break
            }
        }
    }

    // SECOND HALF OF ROUND
    // find all elves which would move to same position and cancel their movement
    elves.groupBy { it.plannedPosition }
        .map { it.value }
        .filter { it.size > 1 }
        .flatten()
        .forEach { it.cancelPlannedMove() }
    // execute move for other elves
    elves.forEach { it.moveToPlannedPosition() }
}

private object MoveDirectionProvider {

    private val moves = listOf(Heading.NORTH, Heading.SOUTH, Heading.WEST, Heading.EAST)

    fun getForRound(round: Int): List<Heading> {
        val movesForRound = mutableListOf<Heading>()
        for (index in round until round + moves.size) {
            movesForRound += moves[index % moves.size]
        }
        return movesForRound
    }

}

private fun findNeighbours(currentElf: Elf, elf: List<Elf>): Neighbours {
    val neighbours = elf.filter { it.position in currentElf.neighborhoodArea } // could be improved using 2d-tree
        .filter { it != currentElf }
    return Neighbours(neighbours, currentElf.neighborhoodArea)
}

private data class Neighbours(private val neighbours: List<Elf>, private val area: Rect) {

    fun isEmpty(): Boolean = neighbours.isEmpty()

    private val inNorth: List<Elf>
        get() = neighbours.filter { it.position.y == area.yRange.first }
    private val inSouth: List<Elf>
        get() = neighbours.filter { it.position.y == area.yRange.last }
    private val inWest: List<Elf>
        get() = neighbours.filter { it.position.x == area.xRange.first }
    private val inEast: List<Elf>
        get() = neighbours.filter { it.position.x == area.xRange.last }

    fun getNeighborsInDirection(direction: Heading): List<Elf> {
        return when (direction) {
            Heading.NORTH -> inNorth
            Heading.SOUTH -> inSouth
            Heading.WEST -> inWest
            Heading.EAST -> inEast
        }
    }
}

private fun countEmptySpaces(elves: List<Elf>): Long {
    val minX = elves.minOf { it.position.x }
    val maxX = elves.maxOf { it.position.x } + 1
    val minY = elves.minOf { it.position.y }
    val maxY = elves.maxOf { it.position.y } + 1
    return (maxX - minX) * (maxY - minY) - elves.size
}
