package aoc2024.day16

import utils.Rect
import utils.Vector2
import java.util.PriorityQueue

/**
 * My solution for day 16 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/16">AoC page</a>.
 */
object Day16 {

    fun part1(input: List<String>): Long {
        val maze = Maze(input.map { row -> row.map { char -> char != '#' } })
        val initialRenderState = RenderState(maze.startLocation, RIGHT, 0)

        val bestLocationCost = mutableMapOf<Vector2, RenderState>()
        val upcomingRenderStates = PriorityQueue<RenderState> { first, second -> (first.cost - second.cost).toInt() }

        var renderState: RenderState? = initialRenderState
        while (renderState != null && renderState.location != maze.targetLocation) {
            renderState.getPossibleNextStates(maze)
                .filter { !bestLocationCost.containsKey(it.location) || it.cost < bestLocationCost[it.location]!!.cost }
                .forEach {
                    bestLocationCost[it.location] = it
                    upcomingRenderStates += it
                }
            renderState = upcomingRenderStates.poll()
        }

        return renderState?.cost ?: 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

}

private data class Maze(private val data: List<List<Boolean>>) {

    val dimensions: Rect = Rect(data[0].size, data.size)

    val targetLocation: Vector2 = Vector2(dimensions.width - 2, 1)
    val startLocation: Vector2 = Vector2(1, dimensions.height - 2)

    fun canMoveTo(x: Int, y: Int): Boolean = data[y][x]

    fun canMoveTo(loc: Vector2): Boolean = canMoveTo(loc.x.toInt(), loc.y.toInt())

}


private data class RenderState(val location: Vector2, val direction: Vector2, val cost: Long) {

    fun getPossibleNextStates(maze: Maze): List<RenderState> {
        val leftDirection = direction.turnLeft()
        val rightDirection = direction.turnRight()

        return listOf(
            RenderState(location + direction, direction, cost + 1),
            RenderState(location + leftDirection, leftDirection, cost + 1001),
            RenderState(location + rightDirection, rightDirection, cost + 1001)
        ).filter {
            maze.canMoveTo(it.location)
        }
    }

}


private val LEFT = Vector2(-1, 0)
private val RIGHT = Vector2(1, 0)
private val UP = Vector2(0, -1)
private val DOWN = Vector2(0, 1)

private val DIRECTIONS = listOf(RIGHT, DOWN, LEFT, UP)

private fun Vector2.turnRight(): Vector2 {
    val index = DIRECTIONS.indexOf(this)
    if (index < 0) {
        throw RuntimeException("Vector is not a direction")
    }

    return if (index == DIRECTIONS.lastIndex) {
        DIRECTIONS.first()
    } else {
        DIRECTIONS[index + 1]
    }
}

private fun Vector2.turnLeft(): Vector2 {
    val index = DIRECTIONS.indexOf(this)
    if (index < 0) {
        throw RuntimeException("Vector is not a direction")
    }

    return if (index == 0) {
        DIRECTIONS.last()
    } else {
        DIRECTIONS[index - 1]
    }
}
