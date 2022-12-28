package aoc2022

import utils.Vector2

val moveUp = Vector2(0, -1)
val moveLeft = Vector2(-1, 0)
val moveRight = Vector2(1, 0)

object Day14 {

    fun part1(input: List<String>): Int {
        val sandStart = Vector2(500, 0)
        val blockedFields = input.flatMap { parse(it) }.toMutableSet()

        var counter = 0
        while (true) {
            blockedFields += getEndLocation(sandStart, blockedFields) ?: return counter
            counter++
        }
    }

    fun part2(input: List<String>): Int {
        val sandStart = Vector2(500, 0)

        val walls = input.flatMap { parse(it) }
        val floorY = walls.maxOf { it.y } + 2
        val sand = mutableSetOf(sandStart)

        var width = 0
        for (y in 1 until floorY) {
            width++
            for (x in (sandStart.x - width)..(sandStart.x + width)) {
                val current = Vector2(x, y)
                if (current !in walls && sand.any { it.y == y - 1 && it.x in (x - 1)..(x + 1) }) sand += current
            }
        }
        return sand.size
    }
}

private fun parse(line: String): List<Vector2> {
    return line.split(" -> ")
        .asSequence()
        .map { it.split(",") }
        .map { Vector2(it[0].toInt(), it[1].toInt()) }
        .windowed(2)
        .map { it.sorted() }
        .flatMap { it[0]..it[1] }
        .toList()
}

private fun getEndLocation(startLocation: Vector2, markedFields: Set<Vector2>): Vector2? {

    // fall down
    val currentSandLocation = markedFields.filter { it.x == startLocation.x && it.y > startLocation.y }
        .minByOrNull { it.y } ?: return null

    // check left
    val left = (currentSandLocation + moveLeft)
    val right = (currentSandLocation + moveRight)
    return if (left !in markedFields) {
        getEndLocation(left, markedFields)
    } else if (right !in markedFields) {
        getEndLocation(right, markedFields)
    } else {
        currentSandLocation + moveUp
    }
}
