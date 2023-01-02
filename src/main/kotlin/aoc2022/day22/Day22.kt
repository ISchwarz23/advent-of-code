package aoc2022.day22

import utils.*

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

private fun calculatePassword(endLocation: Vector2, endFacing: Heading) =
    1000 * endLocation.y + 4 * endLocation.x + endFacing.value

private fun executeMovementInstructions(
    mapOfTheBoard: Map<Vector2, FieldType>,
    moves: List<MoveInstruction>,
    wrappingStrategy: WrappingStrategy,
    startLocation: Vector2 = Vector2(mapOfTheBoard.keys.filter { it.y == 1L }.minOf { it.x }, 1),
    startFacing: Heading = Heading.EAST
): Pair<Vector2, Heading> {
    var currentLocation = startLocation
    var currentFacing = startFacing

    // used for debugging
    val path = mutableMapOf<Vector2, Heading>()

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

private fun printDebugInfo(path: Map<Vector2, Heading>, mapOfTheBoard: Map<Vector2, FieldType>) {
    val width = mapOfTheBoard.keys.maxOf { it.x }
    val height = mapOfTheBoard.keys.maxOf { it.y }
    for (y in 1..height) {
        for (x in 1..width) {
            val location = Vector2(x, y)
            if (path.contains(location)) {
                when (path[location]) {
                    Heading.EAST -> print("${ANSI_GREEN}>${ANSI_RESET}")
                    Heading.SOUTH -> print("${ANSI_GREEN}v${ANSI_RESET}")
                    Heading.WEST -> print("${ANSI_GREEN}<${ANSI_RESET}")
                    Heading.NORTH -> print("${ANSI_GREEN}^${ANSI_RESET}")
                    null -> error("Unknown Heading")
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

    fun apply(currentLocation: Vector2, currentFacing: Heading): Pair<Vector2, Heading>

    object HardcodedEdges : WrappingStrategy {

        private val rules = mutableListOf<TranslationRule>()

        init {
            // rules for example (only required ones)
            rules += TranslationRule(Rect(13..13, 5..8), Heading.EAST) {
                val normedVal = it.y - 4
                Pair(Vector2(17 - normedVal, 9), Heading.SOUTH)
            }
            rules += TranslationRule(Rect(9..12, 13..13), Heading.SOUTH) {
                val normedVal = it.x - 8
                Pair(Vector2(5 - normedVal, 8), Heading.NORTH)
            }
            rules += TranslationRule(Rect(5..8, 4..4), Heading.NORTH) {
                val normedVal = it.x - 4
                Pair(Vector2(9, normedVal), Heading.EAST)
            }

            // rules for given input (clockwise)
            rules += TranslationRule(Rect(51..100, 0..0), Heading.NORTH) {
                val normedVal = it.x - 50
                Pair(Vector2(1, 150 + normedVal), Heading.EAST)
            }
            rules += TranslationRule(Rect(101..150, 0..0), Heading.NORTH) {
                val normedVal = it.x - 100
                Pair(Vector2(normedVal, 200), Heading.NORTH)
            }
            rules += TranslationRule(Rect(151..151, 1..50), Heading.EAST) {
                val normedVal = it.y
                Pair(Vector2(100, 151 - normedVal), Heading.WEST)
            }
            rules += TranslationRule(Rect(101..150, 51..51), Heading.SOUTH) {
                val normedVal = it.x - 100
                Pair(Vector2(100, 50 + normedVal), Heading.WEST)
            }
            rules += TranslationRule(Rect(101..101, 51..100), Heading.EAST) {
                val normedVal = it.y - 50
                Pair(Vector2(100 + normedVal, 50), Heading.NORTH)
            }
            rules += TranslationRule(Rect(101..101, 101..150), Heading.EAST) {
                val normedVal = it.y - 100
                Pair(Vector2(150, 51 - normedVal), Heading.WEST)
            }
            rules += TranslationRule(Rect(51..100, 151..151), Heading.SOUTH) {
                val normedVal = it.x - 50
                Pair(Vector2(50, 150 + normedVal), Heading.WEST)
            }
            rules += TranslationRule(Rect(51..51, 151..200), Heading.EAST) {
                val normedVal = it.y - 150
                Pair(Vector2(50 + normedVal, 150), Heading.NORTH)
            }
            rules += TranslationRule(Rect(1..50, 201..201), Heading.SOUTH) {
                val normedVal = it.x
                Pair(Vector2(100 + normedVal, 1), Heading.SOUTH)
            }
            rules += TranslationRule(Rect(0..0, 151..200), Heading.WEST) {
                val normedVal = it.y - 150
                Pair(Vector2(50 + normedVal, 1), Heading.SOUTH)
            }
            rules += TranslationRule(Rect(0..0, 101..150), Heading.WEST) {
                val normedVal = it.y - 100
                Pair(Vector2(51, 51 - normedVal), Heading.EAST)
            }
            rules += TranslationRule(Rect(1..50, 100..100), Heading.NORTH) {
                val normedVal = it.x
                Pair(Vector2(51, 50 + normedVal), Heading.EAST)
            }
            rules += TranslationRule(Rect(50..50, 51..100), Heading.WEST) {
                val normedVal = it.y - 50
                Pair(Vector2(normedVal, 101), Heading.SOUTH)
            }
            rules += TranslationRule(Rect(50..50, 1..50), Heading.WEST) {
                val normedVal = it.y
                Pair(Vector2(1, 151 - normedVal), Heading.EAST)
            }
        }

        override fun apply(currentLocation: Vector2, currentFacing: Heading): Pair<Vector2, Heading> {
            val newLocation = currentLocation + currentFacing.movementVector
            val translationRule = rules.find { currentFacing == it.matchingFacing && newLocation in it.matchingRect }
                ?: error("Missing TranslationRule for '$newLocation' facing '$currentFacing'")
            return translationRule.translate(newLocation)
        }

        data class TranslationRule(
            val matchingRect: Rect,
            val matchingFacing: Heading,
            val translate: (location: Vector2) -> Pair<Vector2, Heading>
        )
    }

    class BeamingEdges(private val mapOfTheBoard: Map<Vector2, FieldType>) : WrappingStrategy {

        override fun apply(currentLocation: Vector2, currentFacing: Heading): Pair<Vector2, Heading> {
            val newLocation = when (currentFacing) {
                Heading.EAST -> {
                    val newY = currentLocation.y
                    val newX = mapOfTheBoard.keys.filter { it.y == newY }.minOf { it.x }
                    Vector2(newX, newY)
                }

                Heading.SOUTH -> {
                    val newX = currentLocation.x
                    val newY = mapOfTheBoard.keys.filter { it.x == newX }.minOf { it.y }
                    Vector2(newX, newY)
                }

                Heading.WEST -> {
                    val newY = currentLocation.y
                    val newX = mapOfTheBoard.keys.filter { it.y == newY }.maxOf { it.x }
                    Vector2(newX, newY)
                }

                Heading.NORTH -> {
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
    currentFacing: Heading,
    wrappingStrategy: WrappingStrategy
): Pair<Vector2, Heading> {
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


fun Heading.turn(direction: TurnDirection): Heading {
    return when (direction) {
        TurnDirection.LEFT -> turn90DegreeLeft()
        TurnDirection.RIGHT -> turn90DegreeRight()
        TurnDirection.NONE -> this
    }
}

val Heading.value: Int
    get() {
        var index = Heading.values().indexOf(this)
        index -= 1
        if (index < 0) index += Heading.values().size
        return index
    }