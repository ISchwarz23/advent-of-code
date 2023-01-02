package aoc2021

object Day12 {

    fun part1(caveConnections: Map<String, List<String>>): Int {
        return findRoutes(caveConnections) { route, caveToVisit ->
            caveToVisit.isBigCave || route.containsNot(caveToVisit)
        }.size
    }

    fun part2(caveConnections: Map<String, List<String>>): Int {
        val cleanedCaveConnections = caveConnections.mapValues { it.value.filter { cave -> cave != "start" } }
        return findRoutes(cleanedCaveConnections) { route, caveToVisit ->
            caveToVisit.isBigCave || route.containsNot(caveToVisit) || route.containsNoDuplicateSmallCave()
        }.size
    }

    private fun findRoutes(
        caveConnections: Map<String, List<String>>,
        visitingRule: (route: Route, caveToVisit: String) -> Boolean
    ): List<Route> {

        val foundRoutes = mutableListOf<Route>()
        val progressingRoutes = mutableListOf<Route>()
        progressingRoutes += Route(listOf("start"))

        while (progressingRoutes.isNotEmpty()) {
            val currentRoute = progressingRoutes.removeFirst()
            val connectedCaves: List<String> = caveConnections[currentRoute.lastCaveWaypoint] ?: emptyList()
            for (cave in connectedCaves) {
                if (visitingRule.invoke(currentRoute, cave)) {
                    val extendedRoute = currentRoute + cave
                    if (extendedRoute.isAtEnd) foundRoutes += extendedRoute
                    else progressingRoutes += extendedRoute
                }
            }
        }
        return foundRoutes
    }

}


private val String.isBigCave: Boolean
    get() = this.toCharArray().first().isUpperCase()
private val String.isSmallCave: Boolean
    get() = this.isBigCave.not()

class Route(private val caveWaypoints: List<String> = emptyList()) {

    val lastCaveWaypoint: String
        get() = caveWaypoints.last()

    val isAtEnd: Boolean
        get() = lastCaveWaypoint == "end"

    fun containsNoDuplicateSmallCave(): Boolean {
        val smallCaveWaypoints = caveWaypoints.filter { it.isSmallCave }
        return smallCaveWaypoints.size == smallCaveWaypoints.distinct().size
    }

    operator fun plus(newCaveWaypoint: String): Route {
        return Route(caveWaypoints + newCaveWaypoint)
    }

    override fun toString(): String {
        return "$caveWaypoints"
    }

    fun contains(cave: String): Boolean {
        return caveWaypoints.contains(cave)
    }

    fun containsNot(cave: String): Boolean {
        return caveWaypoints.contains(cave).not()
    }
}