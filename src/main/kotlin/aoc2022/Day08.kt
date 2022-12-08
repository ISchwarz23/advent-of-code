package aoc2022

object Day08 {

    fun part1(input: List<List<Int>>): Int {
        val forest = Forest(input)
        return forest.getCoordinateIterator().count { isVisible(it.first, it.second, forest) }
    }

    fun part2(input: List<List<Int>>): Int {
        val forest = Forest(input)
        return forest.getCoordinateIterator().maxOf { getScenicScore(it.first, it.second, forest) }
    }
}

private fun getScenicScore(x: Int, y: Int, forest: Forest): Int {
    return getViewDistance(forest.getColumn(x), y) { it - 1 } * // distance top
            getViewDistance(forest.getColumn(x), y) { it + 1 } * // distance bottom
            getViewDistance(forest.getRow(y), x) { it - 1 } * // distance left
            getViewDistance(forest.getRow(y), x) { it + 1 } // distance right
}

private fun getViewDistance(viewLine: List<Int>, startPos: Int, getNextPos: (Int) -> Int): Int {
    var viewDistance = 0
    var currentPos = startPos
    while (currentPos > 0 && currentPos < viewLine.size - 1) {
        currentPos = getNextPos(currentPos)
        viewDistance++
        if (viewLine[currentPos] >= viewLine[startPos]) return viewDistance
    }
    return viewDistance
}

private fun isVisible(x: Int, y: Int, forest: Forest): Boolean {
    // check on boarder
    if (y == 0 || y == forest.height - 1) return true
    if (x == 0 || x == forest.width - 1) return true

    // check inside
    return isVisible(forest.getColumn(x), y) { it - 1 } // check top
            || isVisible(forest.getColumn(x), y) { it + 1 } // check bottom
            || isVisible(forest.getRow(y), x) { it - 1 } // check left
            || isVisible(forest.getRow(y), x) { it + 1 } // check right
}

private fun isVisible(viewLine: List<Int>, startPos: Int, getNextPos: (Int) -> Int): Boolean {
    var currentPos = startPos
    while (currentPos > 0 && currentPos < viewLine.size - 1) {
        currentPos = getNextPos(currentPos)
        if (viewLine[currentPos] >= viewLine[startPos]) return false
    }
    return true
}

class Forest(private val trees: List<List<Int>>) {

    val height: Int = trees.size
    val width: Int = trees[0].size

    fun getRow(y: Int): List<Int> {
        return trees[y]
    }

    fun getColumn(x: Int): List<Int> {
        return trees.indices.map { y -> trees[y][x] }
    }

    fun getTreeHeight(x: Int, y: Int): Int {
        return trees[y][x]
    }

    fun getCoordinateIterator(): List<Pair<Int, Int>> {
        val yRange = trees.indices
        val xRange = trees[0].indices
        return xRange.flatMap { x -> yRange.map { y -> Pair(x, y) } }
    }

}