package aoc2024.day15

import utils.Rect
import utils.Vector2
import utils.split

/**
 * My solution for day 15 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/15">AoC page</a>.
 */
object Day15 {

    fun part1(input: List<String>, logSteps: Boolean = false): Long {
        val (warehouseInput, movementInput) = input.split { it.isEmpty() }
        val warehouse = parseWarehousePart1(warehouseInput)
        val movements = movementInput.joinToString("").toCharArray().map { movementFromChar(it) }

        runMovements(warehouse, movements, logSteps)

        return warehouse.boxPositions.sumOf { it.yRange.first * 100 + it.xRange.first }
    }

    fun part2(input: List<String>, logSteps: Boolean = false): Long {
        val (warehouseInput, movementInput) = input.split { it.isEmpty() }
        val warehouse = parseWarehousePart2(warehouseInput)
        val movements = movementInput.joinToString("").toCharArray().map { movementFromChar(it) }

        runMovements(warehouse, movements, logSteps)

        return warehouse.boxPositions.sumOf { it.yRange.first * 100 + it.xRange.first }
    }

    private fun runMovements(warehouse: Warehouse, movements: List<Vector2>, logSteps: Boolean) {

        if (logSteps) {
            println("Initial State")
            warehouse.print()
            println()
        }

        for (movement in movements) {
            val robotPosition = warehouse.robotLocation
            var itemsToMoveNext = listOf(WarehouseItem(robotPosition, WarehouseItemType.ROBOT))
            val itemsToMove = mutableListOf<WarehouseItem>()
            itemsToMove.addAll(itemsToMoveNext)

            do {
                itemsToMoveNext = itemsToMoveNext.flatMap { itemToMove ->
                    warehouse.getItemsOverlapping(
                        itemToMove.bounds.move(movement)
                    ).filter { it != itemToMove }
                }
                itemsToMove += itemsToMoveNext
            } while (!(itemsToMoveNext.isEmpty() || itemsToMoveNext.any { it.canBeMoved.not() }))

            if (itemsToMove.all { it.canBeMoved }) {
                itemsToMove.distinct().forEach { warehouse.moveItem(it, movement) }
            }

            if (logSteps) {
                println("Movement: $movement")
                warehouse.print()
                println()
            }
        }
    }

}

private fun movementFromChar(char: Char): Vector2 {
    return when (char) {
        '<' -> Vector2(-1, 0)
        '>' -> Vector2(1, 0)
        '^' -> Vector2(0, -1)
        'v' -> Vector2(0, 1)
        else -> throw RuntimeException("Invalid movement: '$char'")
    }
}

private fun parseWarehousePart1(warehouseInput: List<String>): Warehouse {
    val items = warehouseInput.flatMapIndexed { y, row ->
        row.mapIndexed { x, item ->
            when (item) {
                '#' -> WarehouseItem(Rect(x, 1, y, 1), WarehouseItemType.WALL)
                'O' -> WarehouseItem(Rect(x, 1, y, 1), WarehouseItemType.BOX)
                '@' -> WarehouseItem(Rect(x, 1, y, 1), WarehouseItemType.ROBOT)
                else -> null
            }
        }
    }.filterNotNull().toMutableList()
    return Warehouse(items, Rect(warehouseInput[0].length, warehouseInput.size))
}

private fun parseWarehousePart2(warehouseInput: List<String>): Warehouse {
    val items = warehouseInput.flatMapIndexed { y, row ->
        row.mapIndexed { x, item ->
            when (item) {
                '#' -> WarehouseItem(Rect(x * 2, 2, y, 1), WarehouseItemType.WALL)
                'O' -> WarehouseItem(Rect(x * 2, 2, y, 1), WarehouseItemType.BOX)
                '@' -> WarehouseItem(Rect(x * 2, 1, y, 1), WarehouseItemType.ROBOT)
                else -> null
            }
        }
    }.filterNotNull().toMutableList()
    return Warehouse(items, Rect(warehouseInput[0].length * 2, warehouseInput.size))
}


private data class Warehouse(private val items: MutableList<WarehouseItem>, val bounds: Rect) {

    val robotLocation: Rect
        get() = items.filter { it.type == WarehouseItemType.ROBOT }.map { (pos, _) -> pos }.first()


    val boxPositions: List<Rect>
        get() = items.filter { it.type == WarehouseItemType.BOX }.map { (pos, _) -> pos }

    fun getItemsOverlapping(bounds: Rect): List<WarehouseItem> = items.filter { it.bounds overlaps bounds }

    fun getItemsTypeAt(x: Long, y: Long): WarehouseItemType? = getItemsTypeAt(Vector2(x, y))

    fun getItemsTypeAt(location: Vector2): WarehouseItemType? = getItemAt(location)?.type

    fun getItemAt(x: Long, y: Long): WarehouseItem? = getItemAt(Vector2(x, y))

    fun getItemAt(location: Vector2): WarehouseItem? {
        return getItemsOverlapping(Rect(location.x, 1, location.y, 1)).firstOrNull()
    }

    fun print() {
        for (y in bounds.yRange) {
            for (x in bounds.xRange) {
                val item = getItemAt(x, y)
                when (item?.type) {
                    null -> print(".")
                    WarehouseItemType.WALL -> print("#")
                    WarehouseItemType.BOX ->
                        if (item.bounds.width == 2L)
                            if (item.bounds.xRange.first == x)
                                print("[")
                            else
                                print("]")
                        else
                            print("O")
                    WarehouseItemType.ROBOT -> print("@")
                }
            }
            println()
        }
    }

    fun moveItem(item: WarehouseItem, movement: Vector2) {
        items.remove(item)
        items.add(WarehouseItem(item.bounds.move(movement), item.type))
    }

}

private data class WarehouseItem(val bounds: Rect, val type: WarehouseItemType) {
    val canBeMoved: Boolean
        get() = type.canBeMoved
}

private enum class WarehouseItemType(val canBeMoved: Boolean) {
    WALL(false), BOX(true), ROBOT(true)
}
