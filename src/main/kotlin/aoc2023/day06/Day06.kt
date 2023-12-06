package aoc2023.day06

/**
 * My solution for day 6 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/6">AoC page</a>.
 */
object Day06 {

    fun part1(input: List<String>): Long {
        val times = input[0].split("\\s+".toRegex()).drop(1).map { it.toLong() }
        val distances = input[1].split("\\s+".toRegex()).drop(1).map { it.toLong() }
        val races = times.indices.map { Race(times[it], distances[it]) }
        return races.map { getNumberOfPossibleWins(it) }.fold(1) { value, numberOfWins -> value * numberOfWins }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].split("\\s+".toRegex()).drop(1).joinToString("").toLong()
        val distance = input[1].split("\\s+".toRegex()).drop(1).joinToString("").toLong()
        return getNumberOfPossibleWins(Race(time, distance))
    }

}

private fun getNumberOfPossibleWins(race: Race): Long {
    val loadingTimeOfFirstWinningRace = (0 until race.time).asSequence()
        .map { speed -> speed * (race.time - speed) }
        .indexOfFirst { distance -> race.distance < distance }
    return (race.time + 1) - (2 * loadingTimeOfFirstWinningRace)
}

data class Race(
    val time: Long,
    val distance: Long
)
