package aoc2022.day22

import utils.ANSI_GREEN
import utils.ANSI_RESET
import utils.Rect
import utils.Vector2

object Day22 {

    fun part1(map: Map<Vector2, FieldType>, moves: List<MoveInstruction>): Long {
        val (finalLocation, finalFacing) = executeMovementInstructions(map, moves, WrappingStrategy.BeamingEdges(map))
        return calculatePassword(finalLocation, finalFacing)
    }

    fun part2(map: Map<Vector2, FieldType>, moves: List<MoveInstruction>): Long {
        val (finalLocation, finalFacing) = executeMovementInstructions(map, moves, WrappingStrategy.HardcodedEdges)
        return calculatePassword(finalLocation, finalFacing)
    }
}

private fun calculatePassword(endLocation: Vector2, endFacing: Facing) =
    1000 * endLocation.y + 4 * endLocation.x + endFacing.value

private fun executeMovementInstructions(
    mapOfTheBoard: Map<Vector2, FieldType>,
    moves: List<MoveInstruction>,
    wrappingStrategy: WrappingStrategy,
    startLocation: Vector2 = Vector2(mapOfTheBoard.keys.filter { it.y == 1L }.minOf { it.x }, 1),
    startFacing: Facing = Facing.RIGHT
): Pair<Vector2, Facing> {
    var currentLocation = startLocation
    var currentFacing = startFacing

    // used for debugging
    val path = mutableMapOf<Vector2, Facing>()

    for (move in moves) {
        currentFacing = currentFacing.turn(move.turnDirection)
        path[currentLocation] = currentFacing
        repeat(move.numberOfSteps) {
            val (newLocation, newFacing) = executeMovementInstruction(
                mapOfTheBoard,
                currentLocation,
                currentFacing,
                wrappingStrategy
            )
            if (newLocation == currentLocation) return@repeat
            currentLocation = newLocation
            currentFacing = newFacing

            // used for debugging
            path[currentLocation] = currentFacing
        }
    }

    // used for debugging
    printDebugInfo(path, mapOfTheBoard)

    return Pair(currentLocation, currentFacing)
}

private fun printDebugInfo(path: Map<Vector2, Facing>, mapOfTheBoard: Map<Vector2, FieldType>) {
    val width = mapOfTheBoard.keys.maxOf { it.x }
    val height = mapOfTheBoard.keys.maxOf { it.y }
    for (y in 1..height) {
        for (x in 1..width) {
            val location = Vector2(x, y)
            if (path.contains(location)) {
                when (path[location]) {
                    Facing.RIGHT -> print("${ANSI_GREEN}>${ANSI_RESET}")
                    Facing.DOWN -> print("${ANSI_GREEN}v${ANSI_RESET}")
                    Facing.LEFT -> print("${ANSI_GREEN}<${ANSI_RESET}")
                    Facing.UP -> print("${ANSI_GREEN}^${ANSI_RESET}")
                    null -> error("Unknown Facing")
                }
            } else if (mapOfTheBoard.containsKey(location)) {
                when (mapOfTheBoard[location]) {
                    FieldType.EMPTY -> print(".")
                    FieldType.WALL -> print("#")
                    else -> error("Unknown FieldType")
                }
            } else print(" ")
        }
        println()
    }
    println()
}

private sealed interface WrappingStrategy {

    fun apply(currentLocation: Vector2, currentFacing: Facing): Pair<Vector2, Facing>

    object HardcodedEdges : WrappingStrategy {

        private val rules = mutableListOf<TranslationRule>()

        init {
            // rules for example (only required ones)
            rules += TranslationRule(Rect(13..13, 5..8), Facing.RIGHT) {
                val normedVal = it.y - 4
                Pair(Vector2(17 - normedVal, 9), Facing.DOWN)
            }
            rules += TranslationRule(Rect(9..12, 13..13), Facing.DOWN) {
                val normedVal = it.x - 8
                Pair(Vector2(5 - normedVal, 8), Facing.UP)
            }
            rules += TranslationRule(Rect(5..8, 4..4), Facing.UP) {
                val normedVal = it.x - 4
                Pair(Vector2(9, normedVal), Facing.RIGHT)
            }

            // rules for given input (clockwise)
            rules += TranslationRule(Rect(51..100, 0..0), Facing.UP) {
                val normedVal = it.x - 50
                Pair(Vector2(1, 150 + normedVal), Facing.RIGHT)
            }
            rules += TranslationRule(Rect(101..150, 0..0), Facing.UP) {
                val normedVal = it.x - 100
                Pair(Vector2(normedVal, 200), Facing.UP)
            }
            rules += TranslationRule(Rect(151..151, 1..50), Facing.RIGHT) {
                val normedVal = it.y
                Pair(Vector2(100, 151 - normedVal), Facing.LEFT)
            }
            rules += TranslationRule(Rect(101..150, 51..51), Facing.DOWN) {
                val normedVal = it.x - 100
                Pair(Vector2(100, 50 + normedVal), Facing.LEFT)
            }
            rules += TranslationRule(Rect(101..101, 51..100), Facing.RIGHT) {
                val normedVal = it.y - 50
                Pair(Vector2(100 + normedVal, 50), Facing.UP)
            }
            rules += TranslationRule(Rect(101..101, 101..150), Facing.RIGHT) {
                val normedVal = it.y - 100
                Pair(Vector2(150, 51 - normedVal), Facing.LEFT)
            }
            rules += TranslationRule(Rect(51..100, 151..151), Facing.DOWN) {
                val normedVal = it.x - 50
                Pair(Vector2(50, 150 + normedVal), Facing.LEFT)
            }
            rules += TranslationRule(Rect(51..51, 151..200), Facing.RIGHT) {
                val normedVal = it.y - 150
                Pair(Vector2(50 + normedVal, 150), Facing.UP)
            }
            rules += TranslationRule(Rect(1..50, 201..201), Facing.DOWN) {
                val normedVal = it.x
                Pair(Vector2(100 + normedVal, 1), Facing.DOWN)
            }
            rules += TranslationRule(Rect(0..0, 151..200), Facing.LEFT) {
                val normedVal = it.y - 150
                Pair(Vector2(50 + normedVal, 1), Facing.DOWN)
            }
            rules += TranslationRule(Rect(0..0, 101..150), Facing.LEFT) {
                val normedVal = it.y - 100
                Pair(Vector2(51, 51 - normedVal), Facing.RIGHT)
            }
            rules += TranslationRule(Rect(1..50, 100..100), Facing.UP) {
                val normedVal = it.x
                Pair(Vector2(51, 50 + normedVal), Facing.RIGHT)
            }
            rules += TranslationRule(Rect(50..50, 51..100), Facing.LEFT) {
                val normedVal = it.y - 50
                Pair(Vector2(normedVal, 101), Facing.DOWN)
            }
            rules += TranslationRule(Rect(50..50, 1..50), Facing.LEFT) {
                val normedVal = it.y
                Pair(Vector2(1, 151 - normedVal), Facing.RIGHT)
            }
        }

        override fun apply(currentLocation: Vector2, currentFacing: Facing): Pair<Vector2, Facing> {
            val newLocation = currentLocation + currentFacing.movementVector
            val translationRule = rules.find { currentFacing == it.matchingFacing && newLocation in it.matchingRect }
                ?: error("Missing TranslationRule for '$newLocation' facing '$currentFacing'")
            return translationRule.translate(newLocation)
        }

        data class TranslationRule(
            val matchingRect: Rect,
            val matchingFacing: Facing,
            val translate: (location: Vector2) -> Pair<Vector2, Facing>
        )
    }

    class BeamingEdges(private val mapOfTheBoard: Map<Vector2, FieldType>) : WrappingStrategy {

        override fun apply(currentLocation: Vector2, currentFacing: Facing): Pair<Vector2, Facing> {
            val newLocation = when (currentFacing) {
                Facing.RIGHT -> {
                    val newY = currentLocation.y
                    val newX = mapOfTheBoard.keys.filter { it.y == newY }.minOf { it.x }
                    Vector2(newX, newY)
                }

                Facing.DOWN -> {
                    val newX = currentLocation.x
                    val newY = mapOfTheBoard.keys.filter { it.x == newX }.minOf { it.y }
                    Vector2(newX, newY)
                }

                Facing.LEFT -> {
                    val newY = currentLocation.y
                    val newX = mapOfTheBoard.keys.filter { it.y == newY }.maxOf { it.x }
                    Vector2(newX, newY)
                }

                Facing.UP -> {
                    val newX = currentLocation.x
                    val newY = mapOfTheBoard.keys.filter { it.x == newX }.maxOf { it.y }
                    Vector2(newX, newY)
                }
            }
            return Pair(newLocation, currentFacing)
        }
    }
}

private fun executeMovementInstruction(
    mapOfTheBoard: Map<Vector2, FieldType>,
    currentLocation: Vector2,
    currentFacing: Facing,
    wrappingStrategy: WrappingStrategy
): Pair<Vector2, Facing> {
    val newLocation = currentLocation + currentFacing.movementVector
    val fieldAtLocation = mapOfTheBoard[newLocation]
    return if (fieldAtLocation == null) {
        // left map -> wrap using given strategy
        val (wrappedLocation, newFacing) = wrappingStrategy.apply(currentLocation, currentFacing)
        when (mapOfTheBoard[wrappedLocation] ?: error("Error in WrappingStrategy")) {
            FieldType.EMPTY -> Pair(wrappedLocation, newFacing)
            FieldType.WALL -> Pair(currentLocation, currentFacing)
        }
    } else {
        // still on the map -> check if wall or free
        when (fieldAtLocation) {
            FieldType.EMPTY -> Pair(newLocation, currentFacing)
            FieldType.WALL -> Pair(currentLocation, currentFacing)
        }
    }
}
