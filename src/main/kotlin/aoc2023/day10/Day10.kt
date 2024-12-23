package aoc2023.day10

import utils.Rect
import utils.Vector2

/**
 * My solution for day 10 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/10">AoC page</a>.
 */
object Day10 {

    fun part1(pipeTiles: List<String>): Int {
        return getPipePath(TileMap(pipeTiles)).size / 2
    }

    fun part2(input: List<String>): Int {
        val map = TileMap(input)
        val pipePath = getPipePath(map)

        var numberOfEnclosedTiles = 0
        for (y in map.dimensions.yRange) {
            for (x in map.dimensions.xRange) {
                // ignore tiles that are on the path, as they cannot be inside the path
                if (Vector2(x, y) in pipePath) continue

                val xCoordsOfTilesToCheck = if (x < map.dimensions.width / 2) {
                    // in the left half check pipes to the left of current tile
                    0 until x
                } else {
                    // in the right half check pipes to the right of current tile
                    x + 1 until map.dimensions.width.toInt()
                }

                val pathPipesUntilMapEdge = xCoordsOfTilesToCheck
                    .map { xToCheck -> Vector2(xToCheck, y) }
                    .filter { tileToCheck -> tileToCheck in pipePath } // only respect tiles that are part of the path
                    .map { tileLocation -> map.getPipeAt(tileLocation) }
                    .joinToString("")
                    .replace('S', map.startPipeSubstitute)

                val verticalPipes = pathPipesUntilMapEdge.replace("F-*7".toRegex(), "")
                    .replace("F-*J".toRegex(), "|")
                    .replace("L-*J".toRegex(), "")
                    .replace("L-*7".toRegex(), "|")
                    .replace("-+".toRegex(), "")

                if (verticalPipes.length % 2 == 1) {
                    // if there is an odd number of vertical pipes between the current tile and the edge of the map,
                    // then the current tile is inside the path
                    numberOfEnclosedTiles++
                }
            }
        }
        return numberOfEnclosedTiles
    }

    private fun getPipePath(tiles: TileMap): List<Vector2> {
        val startTile = tiles.startTileLocation

        var move = tiles.startDirections.first()
        var currentTile = startTile
        val path = mutableListOf<Vector2>()
        do {
            currentTile = move.doMove(currentTile)
            path += currentTile

            val currentPipe = tiles.getPipeAt(currentTile)
            move = move.getNewMovement(currentPipe)
        } while (currentPipe != 'S')

        return path
    }

}


val directionRight = Vector2(1, 0)
val directionUp = Vector2(0, -1)
val directionLeft = Vector2(-1, 0)
val directionDown = Vector2(0, 1)

private enum class Movement(
    private val direction: Vector2,
    private val fieldsThatCanBeEntered: List<Char>,
    private val resultingDirection: Map<Char, Vector2>
) {
    RIGHT(directionRight, listOf('-', '7', 'J'), mapOf('7' to directionDown, 'J' to directionUp)),
    UP(directionUp, listOf('|', 'F', '7'), mapOf('F' to directionRight, '7' to directionLeft)),
    LEFT(directionLeft, listOf('-', 'F', 'L'), mapOf('F' to directionDown, 'L' to directionUp)),
    DOWN(directionDown, listOf('|', 'L', 'J'), mapOf('L' to directionRight, 'J' to directionLeft));

    fun canDoMove(map: TileMap, startTile: Vector2): Boolean {
        val end = startTile + direction
        return try {
            map.getPipeAt(end) in fieldsThatCanBeEntered
        } catch (t: Throwable) {
            false
        }
    }

    fun doMove(currentTile: Vector2): Vector2 {
        return currentTile + direction
    }

    fun getNewMovement(currentPipe: Char): Movement {
        val direction = resultingDirection[currentPipe] ?: direction
        return Movement.fromDirection(direction)!!
    }

    companion object {

        fun fromDirection(direction: Vector2): Movement? {
            return Movement.values().firstOrNull { it.direction == direction }
        }

    }
}

private data class Tile(val pipe: Char, val location: Vector2)

private data class TileMap(val tiles: List<String>) {

    val dimensions = Rect(tiles[0].length, tiles.size)

    val startTileLocation = tileSequence().first { (pipe, _) -> pipe == 'S' }.location

    val startDirections = Movement.values().filter { it.canDoMove(this, startTileLocation) }

    val startPipeSubstitute: Char by lazy {
        if (startDirections.containsAll(listOf(Movement.UP, Movement.RIGHT))) {
            'L'
        } else if (startDirections.containsAll(listOf(Movement.UP, Movement.LEFT))) {
            'J'
        } else if (startDirections.containsAll(listOf(Movement.DOWN, Movement.RIGHT))) {
            'F'
        } else if (startDirections.containsAll(listOf(Movement.DOWN, Movement.LEFT))) {
            '7'
        } else {
            throw RuntimeException("Invalid start Pipe")
        }
    }

    fun tileSequence(): Sequence<Tile> {
        return tiles.asSequence()
            .flatMapIndexed { y, row ->
                row.mapIndexed { x, pipe -> Tile(pipe, Vector2(x, y)) }
            }
    }

    fun getPipeAt(position: Vector2): Char = tiles[position.y.toInt()][position.x.toInt()]

}
