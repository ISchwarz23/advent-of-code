package aoc2022.day08

import utils.Heading
import utils.Vector2


object Day08 {

    fun part1(input: List<List<Int>>): Int {
        val forest = Forest(input)
        return forest.getCoordinateIterator().count { isVisible(forest, it) }
    }

    fun part2(input: List<List<Int>>): Int {
        val forest = Forest(input)
        return forest.getCoordinateIterator().maxOf { getScenicScore(forest, it) }
    }
}

private fun getScenicScore(forest: Forest, pos: Vector2): Int {
    return getViewDistance(forest, pos) { it + Heading.NORTH.movementVector } * // distance top
            getViewDistance(forest, pos) { it + Heading.SOUTH.movementVector } * // distance bottom
            getViewDistance(forest, pos) { it + Heading.WEST.movementVector } * // distance left
            getViewDistance(forest, pos) { it + Heading.EAST.movementVector } // distance right
}

private fun getViewDistance(forest: Forest, startPos: Vector2, getNextTreePos: (Vector2) -> Vector2): Int {
    var currentTreePos = getNextTreePos(startPos)
    var distance = 0

    while (currentTreePos in forest) {
        distance++
        if (forest.getTreeHeight(currentTreePos) >= forest.getTreeHeight(startPos)) break
        currentTreePos = getNextTreePos(currentTreePos)
    }
    return distance
}

private fun isVisible(forest: Forest, pos: Vector2): Boolean {
    return forest.isAtBorder(pos) // trees on the forest border are always visible
            || isVisible(forest, pos) { it + Heading.NORTH.movementVector } // check top
            || isVisible(forest, pos) { it + Heading.SOUTH.movementVector } // check bottom
            || isVisible(forest, pos) { it + Heading.WEST.movementVector } // check left
            || isVisible(forest, pos) { it + Heading.EAST.movementVector } // check right
}

private fun isVisible(forest: Forest, startPos: Vector2, getNextTreePos: (Vector2) -> Vector2): Boolean {
    var currentTreePos = getNextTreePos(startPos)
    while (currentTreePos in forest) {
        if (forest.getTreeHeight(currentTreePos) >= forest.getTreeHeight(startPos)) return false
        currentTreePos = getNextTreePos(currentTreePos)
    }
    return true
}
