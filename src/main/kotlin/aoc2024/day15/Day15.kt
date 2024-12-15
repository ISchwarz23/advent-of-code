package aoc2024.day15

import utils.Vector2
import utils.split
import java.text.ParseException

/**
 * My solution for day 15 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/15">AoC page</a>.
 */
object Day15 {

    fun part1(input: List<String>): Long {
        val (warehouseInput, movementInput) = input.split { it.isEmpty() }
        val warehouse = parseWarehouse(warehouseInput)
        val movements = movementInput.joinToString("").toCharArray().map { Movement.fromChar(it) }

        for (movement in movements) {
            // println("Movement: $movement")
            // warehouse.print()
            // println()

            var numberOfMoves = 0
            val robotPosition = warehouse.robotPosition
            var targetPosition = robotPosition

            do {
                targetPosition += movement.vector
                numberOfMoves++
            } while (warehouse.getItemAt(targetPosition) != null && warehouse.getItemAt(targetPosition) != MapItem.WALL)

            if (warehouse.getItemAt(targetPosition) == null) {
                (0 until numberOfMoves).reversed().forEach { distance ->
                    warehouse.moveItemAt(robotPosition + movement.vector * distance, movement)
                }
            }
        }

        return warehouse.boxPositions.sumOf { it.y * 100 + it.x }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

}


private enum class Movement(val vector: Vector2) {
    UP(Vector2(0, -1)),
    DOWN(Vector2(0, 1)),
    LEFT(Vector2(-1, 0)),
    RIGHT(Vector2(1, 0));

    companion object {
        fun fromChar(char: Char): Movement {
            return when (char) {
                '^' -> UP
                'v' -> DOWN
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw ParseException("Invalid direction char: '$char'", 0)
            }
        }
    }
}

private fun parseWarehouse(mapInput: List<String>): Warehouse {
    val mapData = Array(mapInput.size) { rowIndex -> Array<MapItem?>(mapInput[rowIndex].length) { null } }

    mapInput.flatMapIndexed { y, row -> row.mapIndexed { x, itemChar -> Pair(Vector2(x, y), MapItem.fromChar(itemChar)) } }
        .filter { (_, item) -> item != null }
        .forEach { (pos, item) ->
            mapData[pos.y.toInt()][pos.x.toInt()] = item
        }

    return Warehouse(mapData)
}

private data class Warehouse(private val map: Array<Array<MapItem?>>) {

    val robotPosition: Vector2
        get() = items.filter { (_, item) -> item == MapItem.ROBOT }.map { (pos, _) -> pos }.first()


    val boxPositions: List<Vector2>
        get() = items.filter { (_, item) -> item == MapItem.BOX }.map { (pos, _) -> pos }

    private val items: List<Pair<Vector2, MapItem>>
        get() {
            return map.flatMapIndexed { y, row -> row.mapIndexed { x, item -> Pair(Vector2(x, y), item) } }
                .filter { (pos, item) -> item != null }
                .map { (pos, item) -> Pair(pos, item!!) }
        }

    fun getItemAt(position: Vector2): MapItem? = getItemAt(position.x.toInt(), position.y.toInt())

    fun getItemAt(x: Int, y: Int): MapItem? = map[y][x]

    fun moveItemAt(position: Vector2, movement: Movement) {
        val newPosition = position + movement.vector

        if (getItemAt(newPosition) == null) {
            map[newPosition.y.toInt()][newPosition.x.toInt()] = map[position.y.toInt()][position.x.toInt()]
            map[position.y.toInt()][position.x.toInt()] = null
        } else {
            throw RuntimeException("Unable to move item to new position, as it is occupied")
        }
    }

    fun print() {
        for (row in map) {
            for (item in row) {
                when(item) {
                    null -> print(".")
                    MapItem.WALL -> print("#")
                    MapItem.BOX -> print("O")
                    MapItem.ROBOT -> print("@")
                }
            }
            println()
        }
    }

}




private enum class MapItem(val canBeMoved: Boolean) {
    WALL(false),
    BOX(true),
    ROBOT(true);

    companion object {
        fun fromChar(char: Char): MapItem? {
            return when (char) {
                '#' -> WALL
                'O' -> BOX
                '@' -> ROBOT
                else -> null
            }
        }
    }
}
