package aoc2022


object Day16 {

    fun part1(input: List<String>): Long {
        val flowRateByValve = mutableMapOf<String, Int>()
        val tunnelConnections = mutableMapOf<String, List<String>>()
        input.map { parse(it) }.forEach {
            flowRateByValve[it.first] = it.second
            tunnelConnections[it.first] = it.third
        }
        val initialState = VolcanoState("AA", flowRateByValve, tunnelConnections, 30)

        val unfinishedStates = mutableListOf(initialState)
        var best: VolcanoState? = null
        while (unfinishedStates.isNotEmpty() && unfinishedStates.size < 15_000) {
            val state = unfinishedStates.removeAt(0)
            state.getNextStates().forEach {
                if (it.couldOpenMoreValves()) {
                    if (it.potentialReleasedPressure > (best?.releasedPressure ?: 0) && it !in unfinishedStates) {
                        unfinishedStates += it
                    }
                } else if (it.releasedPressure > best?.releasedPressure ?: 0) {
                    best = it
                }
            }
        }

        return best?.releasedPressure ?: 0L
    }

    fun part2(input: List<String>): Int {
        return -1
    }
}

private data class VolcanoState(
    val location: String,
    val flowRateByValve: Map<String, Int>,
    val tunnelConnections: Map<String, List<String>>,
    val timeToEruption: Int,
    val releasedPressure: Long = 0,
) {

    val potentialReleasedPressure: Long = releasedPressure + flowRateByValve.values.sum()

    fun couldOpenMoreValves(): Boolean = timeToEruption > 0 && flowRateByValve.values.any { it > 0 }

    fun getNextStates(): List<VolcanoState> {
        val nextVolcanoStates = mutableListOf<VolcanoState>()

        // open valve at current location
        val remainingTime = timeToEruption - 1
        val flowRateAtCurrentLocation = flowRateByValve[location] ?: 0
        if (flowRateAtCurrentLocation > 0) {
            val pressureReleasedByValve = remainingTime * flowRateAtCurrentLocation
            val newFlowRateByValve = flowRateByValve.toMutableMap()
            newFlowRateByValve[location] = 0
            nextVolcanoStates += VolcanoState(
                location,
                newFlowRateByValve,
                tunnelConnections,
                remainingTime,
                releasedPressure + pressureReleasedByValve,
            )
        }

        // go to other locations
        val remainingLocations = flowRateByValve.entries.filter { it.value > 0 }.map { it.key }
        for (otherValve in remainingLocations) {
            val time = getShortestPath(location, otherValve, tunnelConnections)
            nextVolcanoStates += VolcanoState(
                otherValve,
                flowRateByValve,
                tunnelConnections,
                timeToEruption - time,
                releasedPressure,
            )
        }
        return nextVolcanoStates
    }

}

private fun getShortestPath(
    startLocation: String,
    targetLocation: String,
    tunnelConnections: Map<String, List<String>>
): Int {
    var currentLocations = listOf(startLocation)
    var steps = 0
    while (targetLocation !in currentLocations) {
        steps++
        currentLocations = currentLocations.flatMap { tunnelConnections[it] ?: emptyList() }
    }
    return steps
}

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
