package aoc2024.day12

import utils.Vector2
import utils.increase

/**
 * My solution for day 12 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/12">AoC page</a>.
 */
object Day12 {

    fun part1(input: List<List<Char>>): Long {
        var result = 0L

        val remainingFields = input.flatMapIndexed { y, row -> row.mapIndexed { x, value -> Field(Vector2(x, y), value) } }.toMutableList()
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
        var result = 0L

        val remainingFields = input.flatMapIndexed { y, row -> row.mapIndexed { x, value -> Field(Vector2(x, y), value) } }.toMutableList()
        while (remainingFields.isNotEmpty()) {
            val connectedFields = findConnectedFields(remainingFields.first().value, remainingFields)
            val area = connectedFields.size
            val perimeter = calculateSides(connectedFields)
            result += area * perimeter
            remainingFields.removeAll(connectedFields)
        }

        return result
    }

}

private fun calculatePerimeter(fields: List<Field>): Int {
    return fields.sumOf { f1 -> 4 - fields.count { f2 -> f1 isNeighbourOf f2 } }
}

private fun calculateSides(fields: List<Field>): Int {
    val fieldPositions = fields.map { it.position }
    val edges = fieldPositions.flatMap { position ->
        Side.values().filter { side -> !fieldPositions.contains(position + side.direction) }.map { side -> Edge(position, side) }
    }.groupBy { it.side }

    val noTopEdges = countHorizontalEdges(edges.getOrDefault(Side.TOP, listOf()))
    val noBottomEdges = countHorizontalEdges(edges.getOrDefault(Side.BOTTOM, listOf()))
    val noLeftEdges = countVerticalEdges(edges.getOrDefault(Side.LEFT, listOf()))
    val noRightEdges = countVerticalEdges(edges.getOrDefault(Side.RIGHT, listOf()))
    return noLeftEdges + noRightEdges + noTopEdges + noBottomEdges
}

fun countHorizontalEdges(edges: List<Edge>): Int {
    return edges.groupBy { edge -> edge.position.y } // group by column
        .map { mapping -> mapping.value }
        .map { edgesInsideOneColumn -> edgesInsideOneColumn.map { it.position.x }.sorted() }
        .sumOf { edgeXCoordsInsideColumn -> edgeXCoordsInsideColumn.windowed(2).count {
            (first, second) -> second - first > 1 } + 1  // if X-coordinate gap bigger then 1 -> new edge begins
        }
}

fun countVerticalEdges(edges: List<Edge>): Int {
    return edges.groupBy { edge -> edge.position.x } // group by row
        .map { mapping -> mapping.value }
        .map { edgesInsideOneRow -> edgesInsideOneRow.map { it.position.y }.sorted() }
        .sumOf { edgeYCoordsInsideRow -> edgeYCoordsInsideRow.windowed(2).count {
            (first, second) -> second - first > 1 } + 1  // if Y-coordinate gap bigger then 1 -> new edge begins
        }
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


data class Field(val position: Vector2, val value: Char) {

    infix fun isNeighbourOf(other: Field): Boolean {
        return (this.position - other.position).abs().linearMagnitude() == 1L
    }

}

enum class Side(val direction: Vector2) {
    TOP(Vector2(0, -1)),
    BOTTOM(Vector2(0, 1)),
    LEFT(Vector2(-1, 0)),
    RIGHT(Vector2(1, 0))
}

data class Edge(val position: Vector2, val side: Side)

