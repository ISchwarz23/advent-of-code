package aoc2022.day15

import aoc2022.day04.overlaps
import aoc2022.day12.Coords
import java.lang.Integer.max
import kotlin.math.abs

object Day15 {

    fun part1(entries: List<Pair<Coords, Coords>>, y: Int): Int {
        val beaconPositionX = entries.map { it.second }.filter { it.y == y }.map { it.x }
        return getCheckedCoordsAsRanges(entries, y).flatMap { it.toList() }.filterNot { it in beaconPositionX }.size
    }

    fun part2(entries: List<Pair<Coords, Coords>>, areaSize: Int): Long {
        for (y in 0..areaSize) {
            val checkedCoordsAsRanges = getCheckedCoordsAsRanges(entries, y, 0..areaSize)
            if (checkedCoordsAsRanges.size > 1) {
                return (checkedCoordsAsRanges[0].last + 1).toLong() * 4_000_000 + y
            }
        }
        return 0
    }
}

private fun getCheckedCoordsAsRanges(
    entries: List<Pair<Coords, Coords>>,
    y: Int,
    xRangeLimit: IntRange = Int.MIN_VALUE..Int.MAX_VALUE
): List<IntRange> {
    val checkedRanges = mutableListOf<IntRange>()
    for (entry in entries) {
        val width = entry.getManhattanDistance() - abs(entry.first.y - y)
        if (width > 0) {
            val fromX = entry.first.x - width
            val toX = entry.first.x + width
            if (xRangeLimit.overlaps(fromX..toX)) {
                checkedRanges += (fromX.coerceIn(xRangeLimit)..toX.coerceIn(xRangeLimit))
            }
        }
    }
    val sortedRanges = checkedRanges.sortedWith(compareBy({ it.first }, { it.last }))
    val reducedRanges = mutableListOf<IntRange>()
    var lastRange: IntRange? = null
    for (nextRange in sortedRanges) {
        if (lastRange == null) {
            lastRange = nextRange
        } else if (nextRange.first <= lastRange.last) {
            lastRange = lastRange.first..max(lastRange.last, nextRange.last)
        } else {
            reducedRanges += lastRange
            lastRange = nextRange
        }
    }
    if (reducedRanges.contains(lastRange).not()) reducedRanges += lastRange!!
    return reducedRanges
}

fun Pair<Coords, Coords>.getManhattanDistance(): Int {
    return abs(first.x - second.x) + abs(first.y - second.y)
}

