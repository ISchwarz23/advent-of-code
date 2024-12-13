package aoc2024.day12

import utils.Rect
import utils.Vector2

/**
 * My solution for day 12 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/12">AoC page</a>.
 */
object Day12 {

    fun part1(input: List<List<Char>>): Long {
        val map = Map(input)
        var result = 0L

        val remainingFields = map.fields.toMutableList()
        while (remainingFields.isNotEmpty()) {
            val connectedFields = findConnectedFields(remainingFields.first().value, remainingFields)
            val area = connectedFields.size
            val perimeter = calculatePerimeter(connectedFields)
            result += area * perimeter
            remainingFields.removeAll(connectedFields)
        }

        return result
    }

    fun part2(input: List<List<Char>>): Long {
        return 0
    }

}

private fun calculatePerimeter(fields: List<Field>): Long {
    return fields.sumOf { f1 -> 4L - fields.count { f2 -> f1 isNeighbourOf f2 } }
}

private fun findConnectedFields(value: Char, fields: List<Field>): List<Field> {

    val fieldsWithSameValue = fields.filter { it.value == value }.toMutableList()
    val connectedFields = mutableListOf(fieldsWithSameValue.removeFirst())

    do {
        val neighbourFields = fieldsWithSameValue.filter { potentialNeighbour ->
            connectedFields.any { areaField -> areaField isNeighbourOf potentialNeighbour }
        }

        connectedFields.addAll(neighbourFields)
        fieldsWithSameValue.removeAll(neighbourFields)
    } while (neighbourFields.isNotEmpty())

    return connectedFields
}

data class Map(private val data: List<List<Char>>) {

    val bounds = Rect(data[0].indices, data.indices)

    val fields = data.flatMapIndexed { y, row -> row.mapIndexed { x, value -> Field(Vector2(x, y), value) } }

}

data class Field(val position: Vector2, val value: Char) {

    infix fun isNeighbourOf(other: Field): Boolean {
        return (this.position - other.position).abs().linearMagnitude() == 1L
    }

}

