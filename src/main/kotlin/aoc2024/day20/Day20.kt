package aoc2024.day20

import utils.Vector2

/**
 * My solution for day 20 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/20">AoC page</a>.
 */
object Day20 {

    fun part1(input: List<String>, minimalNoOfPicoSecondsSaved: Int): Int {
        return solve(input, minimalNoOfPicoSecondsSaved, 2)
    }

    fun part2(input: List<String>, minimalNoOfPicoSecondsSaved: Int): Int {
        return solve(input, minimalNoOfPicoSecondsSaved, 20)
    }

    private fun solve(input: List<String>, minimalNoOfPicoSecondsSaved: Int, maximalShortcutLength: Int): Int {
        val elements = input.flatMapIndexed { y, row ->
            row.mapIndexed { x, char -> RaceTrackItem(Vector2(x, y), char) }
        }

        val start = elements.find { it.char == 'S' }?.location ?: throw RuntimeException("No start position given")
        val end = elements.find { it.char == 'E' }?.location ?: throw RuntimeException("No end position given")
        val walls = elements.filter { it.char == '#' }.map { it.location }

        val raceTrack = calculateRaceTrack(start, end, walls)

        return raceTrack.path.flatMap { getShortcutsStartingAt(it, maximalShortcutLength, raceTrack) }
            .count { raceTrack.getSectionLength(it.from, it.to) - it.length >= minimalNoOfPicoSecondsSaved }
    }

}


private fun getShortcutsStartingAt(
    start: Vector2,
    maxShortcutLength: Int,
    raceTrack: RaceTrack
): Set<Shortcut> {
    val firstPointsOfShortcut = listOf(
        start + Vector2(1, 0),
        start + Vector2(-1, 0),
        start + Vector2(0, 1),
        start + Vector2(0, -1),
    ).filter { it in raceTrack.walls }

    val shortcuts = mutableSetOf<Shortcut>()
    for (shortcutStart in firstPointsOfShortcut) {
        shortcuts += raceTrack.path.map { Shortcut(start, it) }
            .filter { it.length > 0 }
            .filter { it.length <= maxShortcutLength }
    }
    return shortcuts
}

private data class Shortcut(val from: Vector2, val to: Vector2) {
    val length: Int by lazy { from.manhattanDistanceTo(to).toInt() }
}

private fun calculateRaceTrack(
    start: Vector2,
    end: Vector2,
    walls: List<Vector2>
): RaceTrack {

    val path = mutableListOf(start)
    while (path.last() != end) {
        val current = path.last()
        path += listOf(
            current + Vector2(1, 0),
            current + Vector2(-1, 0),
            current + Vector2(0, 1),
            current + Vector2(0, -1)
        ).filter { it !in walls }.first { path.size < 2 || it != path[path.size - 2] }
    }
    return RaceTrack(path, walls)
}

private data class RaceTrack(val path: List<Vector2>, val walls: Collection<Vector2>) {

    fun getSectionLength(from: Vector2, to: Vector2): Int {
        return path.indexOf(to) - path.indexOf(from)
    }
}

private data class RaceTrackItem(val location: Vector2, val char: Char)
