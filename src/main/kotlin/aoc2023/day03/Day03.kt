package aoc2023.day03

import utils.Vector2


/**
 * My solution for day 3 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/3">AoC page</a>.
 */
object Day03 {

    fun part1(engineSchematics: List<String>): Int {
        return engineSchematics.flatMapIndexed { rowIndex: Int, row: String -> scanForParts(rowIndex, row) }
            .filter { part -> hasAdjacentSymbol(part, engineSchematics) }
            .sumOf { part -> part.number }
    }

    fun part2(engineSchematics: List<String>): Int {
        val parts = engineSchematics.flatMapIndexed { rowIndex: Int, row: String -> scanForParts(rowIndex, row) }
        return engineSchematics.asSequence()
            .flatMapIndexed { rowIndex: Int, row: String -> scanForGearPositions(rowIndex, row) }
            .map { gearPosition -> gearPosition.toBounds().increase(1) }
            .map { gearBounds -> parts.filter { part -> part.bounds overlaps gearBounds } }
            .filter { partsAdjacentToGear -> partsAdjacentToGear.size == 2 }
            .map { partsAdjacentToGear -> partsAdjacentToGear.fold(1) { value: Int, part: EnginePart -> value * part.number } }
            .sum()
    }

}


private val GEAR_REGEX = "\\*".toRegex()
private val NON_DIGIT_NOR_POINT_REGEX = "[^.0-9]".toRegex()
private val NUMBER_REGEX = "\\d+".toRegex()

private fun scanForGearPositions(rowIndex: Int, row: String): List<Vector2> {
    return GEAR_REGEX.findAll(row).map { Vector2(it.range.first, rowIndex) }.toList()
}

private fun hasAdjacentSymbol(part: EnginePart, engineSchematics: List<String>): Boolean {
    val (xBounds, yBounds) = part.bounds.increaseInsideBounds(
        1,
        0 until engineSchematics[0].length,
        0 until engineSchematics.size
    )
    return yBounds.map { engineSchematics[it] }
        .map { it.substring(xBounds) }
        .any { NON_DIGIT_NOR_POINT_REGEX.find(it) != null }
}

fun scanForParts(rowIndex: Int, row: String): List<EnginePart> {
    return NUMBER_REGEX.findAll(row)
        .map { EnginePart(it.value.toInt(), Bounds(it.range, rowIndex..rowIndex)) }
        .toList()
}

fun Vector2.toBounds(): Bounds {
    return Bounds(
        (this.x.toInt()..this.x.toInt()),
        (this.y.toInt()..this.y.toInt())
    )
}

