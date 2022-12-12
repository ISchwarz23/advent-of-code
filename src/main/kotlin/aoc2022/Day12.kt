package aoc2022

import utils.ANSI_RED
import utils.ANSI_RESET
import utils.ANSI_WHITE
import java.util.*
import kotlin.math.abs

object Day12 {

    fun part1(input: List<String>): Int {
        val map = toTopologyMap(input)
        val shortestPath = getShortestPath(map) ?: error("No path found :(")
        println(toAnsiColoredString(map, shortestPath))
        return shortestPath.size - 1
    }

    fun part2(input: List<String>): Int {
        val map = toTopologyMap(input)
        val shortestPath = map.getCoords('a').mapNotNull { getShortestPath(map, it) }.minByOrNull { it.size } ?: error("No path found :(")
        println(toAnsiColoredString(map, shortestPath))
        return shortestPath.size - 1
    }
}

private fun getShortestPath(
    map: TopologyMap,
    startPosition: Coords = map.startPosition,
    endPosition: Coords = map.endPosition
): List<Coords>? {
    // find the shortest path using A*

    val steps = PriorityQueue<Step> { first, second ->
        (first.remainingDistance + first.stepCount) - (second.remainingDistance + second.stepCount)
    }

    var currentStep: Step? = Step(listOf(startPosition), endPosition.getDistanceTo(startPosition))
    while (currentStep != null && currentStep.remainingDistance > 0) {
        val nextSteps = getPossibleNextSteps(currentStep, map, endPosition)
        nextSteps.forEach { possibleStep ->
            var shallBeAdded = true
            val similarSteps = steps.filter { possibleStep.position in it.path }
            for (similarStep in similarSteps) {
                val otherStepCount = similarStep.path.indexOf(possibleStep.position)
                if (otherStepCount > possibleStep.stepCount) {
                    // new path is faster
                    steps -= similarStep
                } else {
                    // faster path already found
                    shallBeAdded = false
                }
            }
            if (shallBeAdded) steps += possibleStep
        }
        currentStep = steps.poll()
    }
    return currentStep?.path
}

private fun toAnsiColoredString(map: TopologyMap, path: List<Coords>): String {
    var s = ""
    for (y in 0 until map.height) {
        for (x in 0 until map.width) {
            s += when (val coords = Coords(x, y)) {
                in path -> ANSI_RED + map.getRawHeightAt(coords)
                else -> ANSI_WHITE + map.getRawHeightAt(coords)
            }
        }
        s += ANSI_RESET + "\n"
    }
    return s
}

private fun getPossibleNextSteps(currentStep: Step, map: TopologyMap, endPosition: Coords): List<Step> {
    val currentPosition = currentStep.path.last()
    return currentPosition.neighbours.asSequence()
        .filter { it.x in 0 until map.width }
        .filter { it.y in 0 until map.height }
        .filter { getHeightDiff(map, currentPosition, it) in Int.MIN_VALUE..1 }
        .filter { currentStep.path.contains(it).not() }
        .map { Step(currentStep.path + it, it.getDistanceTo(endPosition)) }
        .toList()
}

private fun getHeightDiff(map: TopologyMap, pos1: Coords, pos2: Coords): Int {
    val height1 = map.getHeightAt(pos1)
    val height2 = map.getHeightAt(pos2)
    return height2 - height1
}

private fun toTopologyMap(input: List<String>): TopologyMap {
    return TopologyMap(input.map { it.toList() })
}

data class TopologyMap(private val data: List<List<Char>>) {

    val width: Int = data[0].size
    val height: Int = data.size
    val startPosition: Coords = getCoords('S')[0]
    val endPosition: Coords = getCoords('E')[0]

    fun getCoords(char: Char): List<Coords> {
        val coords = mutableListOf<Coords>()
        data.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if (value == char) {
                    coords += Coords(x, y)
                }
            }
        }
        return coords
    }

    fun getHeightAt(coords: Coords): Int = getHeightAt(coords.x, coords.y)
    private fun getHeightAt(x: Int, y: Int): Int {
        return when (val height = data[y][x]) {
            'S' -> 0
            'E' -> 26
            else -> height.code - 'a'.code
        }
    }

    fun getRawHeightAt(coords: Coords): Char = getRawHeightAt(coords.x, coords.y)
    private fun getRawHeightAt(x: Int, y: Int): Char = data[y][x]
}

data class Coords(val x: Int, val y: Int) {

    private fun getLeftNeighbour() = Coords(x - 1, y)
    private fun getRightNeighbour() = Coords(x + 1, y)
    private fun getUpperNeighbour() = Coords(x, y - 1)
    private fun getLowerNeighbour() = Coords(x, y + 1)
    val neighbours
        get() = listOf(getLeftNeighbour(), getRightNeighbour(), getUpperNeighbour(), getLowerNeighbour())

    fun getDistanceTo(other: Coords): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

}

data class Step(val path: List<Coords>, val remainingDistance: Int) {

    val stepCount = path.size - 1
    val position = path.last()

}