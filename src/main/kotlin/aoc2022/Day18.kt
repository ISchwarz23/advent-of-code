package aoc2022

import utils.Vector3


object Day18 {

    fun part1(input: List<Vector3>): Int {
        return LavaDroplet(input).getNumberOfExteriorSides()
    }

    fun part2(input: List<Vector3>): Int {
        return LavaDroplet(input).getNumberOfExteriorSidesWithoutAirPockets()
    }
}


private class LavaDroplet(private val cubeLocations: List<Vector3>) {

    private val hasPathOutCache: MutableMap<Vector3, Boolean> = mutableMapOf()
    private val nonDiagonalDirections = sequenceOf(
        Vector3(-1, 0, 0),
        Vector3(1, 0, 0),
        Vector3(0, -1, 0),
        Vector3(0, 1, 0),
        Vector3(0, 0, -1),
        Vector3(0, 0, 1)
    )

    val boundingBox: Pair<Vector3, Vector3> by lazy {
        val maxX = cubeLocations.maxOf { it.x }
        val minX = cubeLocations.minOf { it.x }
        val maxY = cubeLocations.maxOf { it.y }
        val minY = cubeLocations.minOf { it.y }
        val maxZ = cubeLocations.maxOf { it.z }
        val minZ = cubeLocations.minOf { it.z }
        Vector3(minX, minY, minZ) to Vector3(maxX, maxY, maxZ)
    }

    fun getNumberOfExteriorSides(): Int {
        return cubeLocations.sumOf { cubeLocation -> nonDiagonalDirections.count { !cubeLocations.contains(cubeLocation + it) } }
    }

    fun getNumberOfExteriorSidesWithoutAirPockets(): Int {
        return cubeLocations.sumOf { cubeLocation -> nonDiagonalDirections.count { hasPathOut(cubeLocation + it) } }
    }

    fun isOutsideBoundingBox(vec: Vector3): Boolean {
        val (min, max) = boundingBox
        return vec.x < min.x || vec.y < min.y || vec.z < min.z || vec.x > max.x || vec.y > max.y || vec.z > max.z
    }

    fun hasPathOut(cubeLocation: Vector3): Boolean {
        // cubes don't have a path out, they are blocked space!
        if (cubeLocations.contains(cubeLocation)) return false
        // return cached result if available
        if (hasPathOutCache.containsKey(cubeLocation)) return hasPathOutCache[cubeLocation]!!

        // find a path out if it exists
        val alreadySearchedOrInvalid = (cubeLocations + cubeLocation).toMutableSet()
        val searchCandidates = mutableSetOf(cubeLocation)
        var frontier = listOf(cubeLocation)
        var hasPathOut = false
        while (frontier.isNotEmpty()) {
            alreadySearchedOrInvalid += frontier
            searchCandidates += frontier
            // found path out, if we found a location with path out, or we are outside the bounding box
            if (frontier.any { hasPathOutCache[it] == true || isOutsideBoundingBox(it) }) {
                hasPathOut = true
                break
            } else {
                // Get next locations to visit, filtering out any that we've already visited.
                frontier = frontier.asSequence()
                    .flatMap { vec -> nonDiagonalDirections.map { vec + it } }
                    .distinct()
                    .filter { !alreadySearchedOrInvalid.contains(it) }
                    .toList()
            }
        }

        // add all candidates to cache (if there is way out or not)
        searchCandidates.forEach { hasPathOutCache[it] = hasPathOut }
        return hasPathOut
    }
}