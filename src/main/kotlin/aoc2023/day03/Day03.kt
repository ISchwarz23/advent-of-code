package aoc2023.day03

import aoc2022.day04.overlaps
import utils.Vector2

/**
 * My solution for day 3 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/3">AoC page</a>.
 */
object Day03 {

    fun part1(engineSchematics: List<String>): Int {
        return engineSchematics.flatMapIndexed { rowIndex: Int, row: String -> scanForPartNumbers(rowIndex, row) }
            .filter { hasAdjacentSymbol(it, engineSchematics) }
            .sumOf { it.partNumber }
    }

    fun part2(engineSchematics: List<String>): Int {
        val partNumbers = engineSchematics.flatMapIndexed { rowIndex: Int, row: String -> scanForPartNumbers(rowIndex, row) }
        return engineSchematics.asSequence()
            .flatMapIndexed { rowIndex: Int, row: String -> scanForGearPositions(rowIndex, row) }
            .map { getPosition -> getPosition.toBounds().increase(1) }
            .map { gearBounds -> partNumbers.filter { part -> part.bounds overlaps gearBounds } }
            .filter { it.size == 2 }
            .map { it.fold(1) { value: Int, part: EnginePart -> value * part.partNumber } }
            .sum()
    }

}

private fun scanForGearPositions(rowIndex: Int, row: String): List<Vector2> {
    return "\\*".toRegex().findAll(row).map { Vector2(it.range.first, rowIndex) }.toList()
}

private fun hasAdjacentSymbol(part: EnginePart, engineSchematics: List<String>): Boolean {
    val (xBounds, yBounds) = part.bounds.increaseInsideBounds(
        1,
        0 until engineSchematics[0].length,
        0 until engineSchematics.size
    )
    return yBounds.map { engineSchematics[it] }
        .map { it.substring(xBounds) }
        .any { "[^.0-9]".toRegex().find(it) != null }
}

fun scanForPartNumbers(y: Int, row: String): List<EnginePart> {
    return "\\d+".toRegex().findAll(row)
        .map { EnginePart(it.value.toInt(), Bounds(it.range, y..y)) }
        .toList()
}

data class EnginePart(
    val partNumber: Int,
    val bounds: Bounds
)

data class Bounds(
    val xBounds: IntRange,
    val yBounds: IntRange
) {

    fun increaseInsideBounds(
        padding: Int = 1,
        xBounds: IntRange = 0..Int.MAX_VALUE,
        yBounds: IntRange = 0..Int.MAX_VALUE
    ): Bounds {
        return Bounds(
            this.xBounds.increaseInsideRange(padding, xBounds),
            this.yBounds.increaseInsideRange(padding, yBounds)
        )
    }

    infix fun overlaps(other: Bounds): Boolean {
        return this.xBounds.overlaps(other.xBounds) && this.yBounds.overlaps(other.yBounds)
    }

}

private fun IntRange.increase(spaceToIncrease: Int): IntRange {
    return (this.first-spaceToIncrease)..(this.last+spaceToIncrease)
}

fun IntRange.increaseInsideRange(padding: Int = 1, range: IntRange = 0..Int.MAX_VALUE): IntRange {
    val newFirst = if (this.first - padding > range.first) this.first - padding else range.first
    val newLast = if (this.last + padding < range.last) this.last + padding else range.last
    return newFirst..newLast
}

fun Vector2.toBounds(
): Bounds {
    return Bounds(
        (this.x.toInt()..this.x.toInt()),
        (this.y.toInt()..this.y.toInt())
    )
}

fun Bounds.increase(
    padding: Int = 1
): Bounds {
    return Bounds(
        this.xBounds.increase(padding),
        this.yBounds.increase(padding)
    )
}
