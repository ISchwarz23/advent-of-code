package aoc2021

import java.util.*

object Day15 {

    fun part1(cave: Cave): Int {
        return getLeastRiskyPath(cave)?.totalRisk ?: -1
    }

    fun part2(input: Cave): Int {
        val cave = input * 5
        return getLeastRiskyPath(cave)?.totalRisk ?: -1
    }

    private fun getLeastRiskyPath(cave: Cave, start: Location = Location(0, 0)): Path? {
        val prioritizedPaths = PriorityPathQueue { p1, p2 -> p1.totalRisk - p2.totalRisk }
        prioritizedPaths += Path(cave, start, 0)
        var currentPath: Path? = prioritizedPaths.poll()
        while (currentPath != null && currentPath.distanceToEnd != 0) {
            prioritizedPaths += currentPath.getNextPossiblePaths()
            currentPath = prioritizedPaths.poll()
        }
        return currentPath
    }

}

private class PriorityPathQueue(comparator: (Path, Path) -> Int) {

    private val paths = PriorityQueue(comparator)
    private val locationToBestPath = mutableMapOf<Location, Path>()

    operator fun plusAssign(path: Path) {
        val bestPath = locationToBestPath[path.currentLocation]
        if (bestPath == null || path.totalRisk < bestPath.totalRisk) {
            paths += path
            locationToBestPath[path.currentLocation] = path
            if (bestPath != null) paths.remove(bestPath)
        }
    }

    operator fun plusAssign(paths: List<Path>) {
        paths.forEach { this += it }
    }

    fun poll(): Path? {
        return paths.poll()
    }
}

class Cave(private val riskLevels: List<List<Int>>) {

    val height = riskLevels.size
    val width = riskLevels[0].size

    fun getRiskAt(location: Location): Int {
        return riskLevels[location.y][location.x]
    }

    operator fun times(i: Int): Cave {
        val newCave = riskLevels.map { it.toMutableList() }.toMutableList()
        // repeat horizontally for i times
        for (y in 0 until height) {
            repeat((i - 1) * width) { x ->
                var value = newCave[y][x] + 1
                if (value > 9) value = 1
                newCave[y] += value
            }
        }
        // repeat vertically for i times
        repeat((i - 1) * height) { y ->
            newCave += newCave[y].map { it + 1 }.map { if (it > 9) 1 else it }.toMutableList()
        }
        return Cave(newCave)
    }
}

data class Location(val x: Int, val y: Int)

private class Path(private val cave: Cave, val currentLocation: Location, val totalRisk: Int) {

    val distanceToEnd = (cave.width - currentLocation.x - 1) + (cave.height - currentLocation.y - 1)

    fun getNextPossiblePaths(): List<Path> {
        val nextSteps = mutableListOf<Location>()
        val x = currentLocation.x
        val y = currentLocation.y

        if (x + 1 < cave.width) nextSteps += Location(x + 1, y)
        if (y + 1 < cave.height) nextSteps += Location(x, y + 1)
        if (x - 1 >= 0) nextSteps += Location(x - 1, y)
        if (y - 1 >= 0) nextSteps += Location(x, y - 1)
        return nextSteps.map { Path(cave, it, totalRisk + cave.getRiskAt(it)) }
    }

    override fun toString(): String {
        return "Path{ currentLocation: ${currentLocation}, totalRisk: $totalRisk, distanceToEnd: $distanceToEnd }"
    }
}