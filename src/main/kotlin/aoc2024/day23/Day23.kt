package aoc2024.day23

/**
 * My solution for day 23 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/23">AoC page</a>.
 */
object Day23 {

    fun part1(input: List<String>): Int {
        val connections = parseConnections(input)

        var networks = connections.keys.filter { pc -> pc[0] == 't' }.map { pc -> listOf(pc) }
        repeat(2) {
            networks = networks.flatMap { network ->
                findPcsConnectedToNetwork(connections, network)
                    .map { connectedPc -> network + connectedPc }
                    .map { it.sorted() }
            }.distinct()
        }
        return networks.size
    }

    fun part2(input: List<String>): String {
        val connections = parseConnections(input)

        var networks = connections.keys.map { pc -> listOf(pc) }
        while (networks.size > 1) {
            networks = networks.flatMap { network ->
                findPcsConnectedToNetwork(connections, network)
                    .map { connectedPc -> network + connectedPc }
                    .map { it.sorted() }
            }.distinct()
        }
        return networks.first().joinToString(",")
    }

}

private fun parseConnections(input: List<String>) = input.map { it.split("-") }
    .flatMap { (pc1, pc2) -> listOf(pc1 to pc2, pc2 to pc1) }
    .groupBy({ (pc1, _) -> pc1 }) { (_, pc2) -> pc2 }

private fun findPcsConnectedToNetwork(connections: Map<String, List<String>>, network: List<String>): List<String> {
    val pcsConnectedToOnePcInNetwork = network.map { pcInNetwork -> connections[pcInNetwork] ?: emptyList() }
    var pcsConnectedToAllPcsInNetwork = pcsConnectedToOnePcInNetwork.first()
    (1 until pcsConnectedToOnePcInNetwork.size).forEach { pcIndex ->
        val pcsConnectedToCurrentPc = pcsConnectedToOnePcInNetwork[pcIndex]
        pcsConnectedToAllPcsInNetwork = pcsConnectedToAllPcsInNetwork.filter { pc -> pc in pcsConnectedToCurrentPc }
    }
    return pcsConnectedToAllPcsInNetwork
}
