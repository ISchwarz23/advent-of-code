package aoc2022

import kotlin.math.max


object Day16 {

    fun part1(input: List<String>): Long {
        cache.clear()
        return getMaxReleasedPressure("AA", 30, parseFlowRateByValve(input), parseCaveMap(input))
    }

    fun part2(input: List<String>): Long {
        cache.clear()

        val caveMap = parseCaveMap(input)
        val flowRateByValve = parseFlowRateByValve(input)
        val relevantValves = flowRateByValve.entries.filter { it.value > 0 }.map { it.key }

        var maxReleasedPressure = 0L
        for(i in 1 until (1 shl relevantValves.size)) {
            if(i % 100 == 0) cache.clear() // required to avoid OutOfMemoryException

            // split work half-half between elf and elephant
            if(i.countOneBits() != relevantValves.size / 2) continue

            // find best result for elephant
            val flowRateByValveElephant = flowRateByValve.toMutableMap()
            relevantValves.filterIndexed { index, _ -> (i and (1 shl index)) != 0 }.forEach { flowRateByValveElephant[it] = 0 }
            val releasePressureElephant = getMaxReleasedPressure("AA", 26, flowRateByValveElephant, caveMap)

            // find best result for elf
            val flowRateByValveElf = flowRateByValve.toMutableMap()
            relevantValves.filterIndexed { index, _ -> (i and (1 shl index)) == 0 }.forEach { flowRateByValveElf[it] = 0 }
            val releasePressureElf = getMaxReleasedPressure("AA", 26, flowRateByValveElf, caveMap)

            // find best result
            maxReleasedPressure = max(maxReleasedPressure, releasePressureElephant + releasePressureElf)
        }
        return maxReleasedPressure
    }
}

private val cache: MutableMap<VolcanoState, Long> = mutableMapOf()

private fun parseFlowRateByValve(input: List<String>): Map<String, Int> {
    return input.map { parse(it) }.associate { it.first to it.second }
}

private fun parseCaveMap(input: List<String>): CaveTunnelMap {
    return CaveTunnelMap(input.map { parse(it) }.associate { it.first to it.third })
}

private fun getMaxReleasedPressure(
    currentLocation: String,
    minutesToEruption: Int,
    flowRateByValve: Map<String, Int>,
    caveMap: CaveTunnelMap
): Long {
    // abort if time is over
    if (minutesToEruption <= 1) return 0L

    // lookup cache
    val state = VolcanoState(currentLocation, flowRateByValve, minutesToEruption)
    if (cache.contains(state)) return cache[state]!!

    // create result collector
    val results = mutableListOf<Long>()

    // open valve at current location
    val flowRateAtCurrentLocation = flowRateByValve[currentLocation] ?: 0
    if (flowRateAtCurrentLocation > 0) {
        val remainingTime = minutesToEruption - 1
        val newFlowRateByValve = flowRateByValve.toMutableMap()
        newFlowRateByValve[currentLocation] = 0
        val pressureReleasedByValve = remainingTime * flowRateAtCurrentLocation
        results += pressureReleasedByValve + getMaxReleasedPressure(
            currentLocation,
            remainingTime,
            newFlowRateByValve,
            caveMap
        )
    }

    // go to other locations
    val remainingLocations =
        flowRateByValve.entries.filter { it.value > 0 }.map { it.key }.filter { it != currentLocation }
    for (nextLocation in remainingLocations) {
        val remainingTime = minutesToEruption - caveMap.getDistanceInMinutes(currentLocation, nextLocation)
        if (remainingTime > 1) {
            results += getMaxReleasedPressure(nextLocation, remainingTime, flowRateByValve, caveMap)
        }
    }

    // cache state
    val bestResult = results.maxOfOrNull { it } ?: 0L
    cache[state] = bestResult
    return bestResult
}

private class CaveTunnelMap(private val tunnelConnections: Map<String, List<String>>) {

    private val cache: MutableMap<Pair<String, String>, Int> = mutableMapOf()

    fun getDistanceInMinutes(startLocation: String, targetLocation: String): Int {
        val connection = if (targetLocation > startLocation) Pair(startLocation, targetLocation) else Pair(
            targetLocation,
            startLocation
        )
        return cache.getOrPut(connection) {
            var currentLocations = listOf(startLocation)
            var steps = 0
            while (targetLocation !in currentLocations) {
                steps++
                currentLocations = currentLocations.flatMap { tunnelConnections[it] ?: emptyList() }
            }
            return@getOrPut steps
        }
    }

}

private data class VolcanoState(
    val location: String,
    val flowRateByValve: Map<String, Int>,
    val timeToEruption: Int
)

private fun parse(input: String): Triple<String, Int, List<String>> {
    val name = input.substringAfter("Valve ").substringBefore(" has ")
    val flowRate = input.substringAfter("has flow rate=").substringBefore(";").toInt()
    val tunnels = if (input.contains("tunnels")) {
        // multiple tunnels
        input.substringAfter("tunnels lead to valves ").split(", ")
    } else {
        // only one tunnel
        listOf(input.substringAfter("tunnel leads to valve "))
    }
    return Triple(name, flowRate, tunnels)
}
