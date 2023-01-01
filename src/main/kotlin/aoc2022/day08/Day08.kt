package aoc2022.day08

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
    return getViewDistance(forest.getTreeColumn(x), y) { it - 1 } * // distance top
            getViewDistance(forest.getTreeColumn(x), y) { it + 1 } * // distance bottom
            getViewDistance(forest.getTreeRow(y), x) { it - 1 } * // distance left
            getViewDistance(forest.getTreeRow(y), x) { it + 1 } // distance right
}

private fun getViewDistance(lineOfTrees: List<Int>, startTreePos: Int, getNextTreePos: (Int) -> Int): Int {
    var viewDistance = 0
    var currentPos = startTreePos
    while (currentPos > 0 && currentPos < lineOfTrees.size - 1) {
        currentPos = getNextTreePos(currentPos)
        viewDistance++
        if (lineOfTrees[currentPos] >= lineOfTrees[startTreePos]) return viewDistance
    }
    return viewDistance
}

private fun isVisible(x: Int, y: Int, forest: Forest): Boolean {
    // check on boarder
    if (y == 0 || y == forest.height - 1) return true
    if (x == 0 || x == forest.width - 1) return true

    // check inside
    return isVisible(forest.getTreeColumn(x), y) { it - 1 } // check top
            || isVisible(forest.getTreeColumn(x), y) { it + 1 } // check bottom
            || isVisible(forest.getTreeRow(y), x) { it - 1 } // check left
            || isVisible(forest.getTreeRow(y), x) { it + 1 } // check right
}

private fun isVisible(lineOfTrees: List<Int>, startTreePos: Int, getNextTreePos: (Int) -> Int): Boolean {
    var currentTreePos = startTreePos
    while (currentTreePos > 0 && currentTreePos < lineOfTrees.size - 1) {
        currentTreePos = getNextTreePos(currentTreePos)
        if (lineOfTrees[currentTreePos] >= lineOfTrees[startTreePos]) return false
    }
    return true
}

class Forest(private val trees: List<List<Int>>) {

    val height: Int = trees.size
    val width: Int = trees[0].size

    fun getTreeRow(y: Int): List<Int> {
        return trees[y]
    }

    fun getTreeColumn(x: Int): List<Int> {
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