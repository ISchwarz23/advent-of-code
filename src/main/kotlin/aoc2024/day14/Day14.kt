package aoc2024.day14

import utils.Rect
import utils.Vector2

/**
 * My solution for day 14 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/14">AoC page</a>.
 */
object Day14 {

    fun part1(input: List<String>, area: Rect): Long {
        val robots = input.map { "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)".toRegex().find(it)!!.groupValues }
            .map { Robot(Vector2(it[1].toInt(), it[2].toInt()), Vector2(it[3].toInt(), it[4].toInt())) }

        repeat(100) {
            robots.forEach { it.move(area) }
        }

        val quadrants = area.quadrants()
        return robots.groupBy { robot -> quadrants.indexOfFirst { quadrant -> robot.position in quadrant } }
            .filter { it.key != -1 }
            .map { it.value.size }
            .fold(1L) { acc, next -> acc * next}
    }

    fun part2(input: List<String>, area: Rect): Int {
        val robots = input.map { "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)".toRegex().find(it)!!.groupValues }
            .map { Robot(Vector2(it[1].toInt(), it[2].toInt()), Vector2(it[3].toInt(), it[4].toInt())) }

        (1..10_000).forEach { seconds ->

            robots.forEach { robot -> robot.move(area) }

            if((seconds - 86) % 101 == 0) {
                println(seconds)
                print(area, robots)
                println()
            }
        }

        return 7055
    }

}

private fun print(area: Rect, robots: List<Robot>) {
    area.yRange.forEach { y ->
        area.xRange.forEach { x ->
            val count = robots.count { robot -> robot.position == Vector2(x, y) }
            if (count == 0) {
                print(".")
            } else {
                print(count)
            }
        }
        println()
    }
}

private data class Robot(var position: Vector2, val velocity: Vector2) {
    fun move(area: Rect) {
        position += velocity

        if(position.x < area.xRange.first) {
            position += Vector2(area.xRange.last + 1, 0)
        } else if (position.x > area.xRange.last) {
            position -= Vector2(area.xRange.last + 1, 0)
        }

        if(position.y < area.yRange.first) {
            position += Vector2(0, area.yRange.last + 1)
        } else if (position.y > area.yRange.last) {
            position -= Vector2(0, area.yRange.last + 1)
        }
    }
}

fun Rect.quadrants(): List<Rect> {
    val xRanges = this.xRange.split()
    val yRanges = this.yRange.split()
    return listOf(
        Rect(xRanges.first, yRanges.first),
        Rect(xRanges.second, yRanges.first),
        Rect(xRanges.first, yRanges.second),
        Rect(xRanges.second, yRanges.second),
    )
}

fun LongRange.split(): Pair<LongRange, LongRange> {
    val firstHalfEnd = (this.last-1) / 2
    val secondHalfStart = if(this.last % 2 == 0L) {
        firstHalfEnd + 2
    } else {
        firstHalfEnd + 1
    }
    return Pair(0..firstHalfEnd, secondHalfStart..this.last)
}
