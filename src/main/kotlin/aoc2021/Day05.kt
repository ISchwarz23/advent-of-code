package aoc2021

import kotlin.math.absoluteValue

object Day05 {

    fun part1(input: List<Line>, debug: Boolean = false): Int {
        val diagram = Diagram()
        input.filter { it.isNotDiagonal }.forEach { diagram.add(it) }
        if (debug) println(diagram)
        return diagram.numberOfOverlappingFields
    }

    fun part2(input: List<Line>, debug: Boolean = false): Int {
        val diagram = Diagram()
        input.forEach { diagram.add(it) }
        if (debug) println(diagram)
        return diagram.numberOfOverlappingFields
    }
}

private class Diagram {
    private val markedFields = mutableMapOf<Coords, Int>()

    operator fun get(x: Int, y: Int): Int {
        return markedFields.getOrDefault(Coords(x, y), 0)
    }

    val numberOfOverlappingFields: Int
        get() = markedFields.filter { it.value > 1 }.size

    fun add(line: Line) {
        val deltaX = line.end.x - line.start.x
        val deltaY = line.end.y - line.start.y
        val steps = if (deltaX.absoluteValue > deltaY.absoluteValue) deltaX.absoluteValue else deltaY.absoluteValue
        val stepSizeX: Double = deltaX.toDouble() / steps
        val stepSizeY: Double = deltaY.toDouble() / steps

        for (i in 0..steps) {
            val x = (line.start.x + stepSizeX * i).toInt()
            val y = (line.start.y + stepSizeY * i).toInt()
            markField(x, y)
        }
    }

    private fun markField(x: Int, y: Int) {
        markField(Coords(x, y))
    }

    private fun markField(coords: Coords) {
        markedFields[coords] = markedFields.getOrDefault(coords, 0) + 1
    }

    override fun toString(): String {
        return toString((markedFields.keys.flatMap { listOf(it.x, it.y) }.maxOrNull() ?: 9) + 1)
    }

    fun toString(size: Int): String {
        var s = ""
        for (y in 0 until size) {
            for (x in 0 until size) {
                val value = markedFields.getOrDefault(Coords(x, y), 0)
                if (value == 0) {
                    s += "."
                } else {
                    s += value
                }
            }
            s += "\n"
        }
        return s
    }
}

data class Line(val start: Coords, val end: Coords) {
    private val isHorizontal: Boolean = start.y == end.y
    private val isVertical: Boolean = start.x == end.x
    val isNotDiagonal: Boolean = isHorizontal || isVertical
}

data class Coords(val x: Int, val y: Int)