package aoc2022


object Day16 {

    fun part1(input: List<String>): Long {
        val flowRateByValve = mutableMapOf<String, Int>()
        val tunnelConnections = mutableMapOf<String, List<String>>()
        input.map { parse(it) }.forEach {
            flowRateByValve[it.first] = it.second
            tunnelConnections[it.first] = it.third
        }
        val map = CaveTunnelMap(tunnelConnections)

        return getMaxReleasedPressure("AA", 30, flowRateByValve, map)
    }

    fun part2(input: List<String>): Int {
        return -1
    }
}

private val cache: MutableMap<VolcanoState, Long> = mutableMapOf()

private fun getMaxReleasedPressure(
    currentLocation: String,
    minutesToEruption: Int,
    flowRateByValve: Map<String, Int>,
    map: CaveTunnelMap
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
            map
        )
    }

    // go to other locations
    val remainingLocations = flowRateByValve.entries.filter { it.value > 0 }.map { it.key }.filter { it != currentLocation }
    for (nextLocation in remainingLocations) {
        val remainingTime = minutesToEruption - map.getDistanceInMinutes(currentLocation, nextLocation)
        if( remainingTime > 1) {
            results += getMaxReleasedPressure(nextLocation, remainingTime, flowRateByValve, map)
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
    val timeToEruption: Int,
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
