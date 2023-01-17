package aoc2022.day08

import utils.Vector2


class Forest(private val trees: List<List<Int>>) {

    val height: Int = trees.size
    val width: Int = trees[0].size

    operator fun contains(pos: Vector2): Boolean {
        return pos.x in 0 until width && pos.y in 0 until height
    }

    fun getTreeHeight(pos: Vector2): Int {
        return trees[pos.y.toInt()][pos.x.toInt()]
    }

    fun getCoordinateIterator(): List<Vector2> {
        val yRange = trees.indices
        val xRange = trees[0].indices
        return xRange.flatMap { x -> yRange.map { y -> Vector2(x, y) } }
    }

    fun isAtBorder(pos: Vector2): Boolean {
        return pos.y == 0L || pos.y == height - 1L || pos.x == 0L || pos.x == width - 1L
    }

}