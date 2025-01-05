package aoc2024.day21

import utils.Vector2


/**
 * My solution for day 21 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/21">AoC page</a>.
 */
object Day21 {

    fun part1(input: List<String>): Long {
        return solve(input, 3)
    }

    fun part2(input: List<String>): Long {
        return solve(input, 26)
    }

    private fun solve(codes: List<String>, lengthOfRobotChain: Int): Long {
        return codes.sumOf { code ->
            getNumericValue(code) * getLeastNumberOfInstructions(Keypad.NUMBERS, code.toList(), lengthOfRobotChain)
        }
    }

}


private fun getNumericValue(code: String): Int {
    return code.takeWhile { char -> char.isDigit() }.toInt()
}

private fun getLeastNumberOfInstructions(
    keypad: Keypad,
    buttonsToPress: List<Char>,
    lengthOfRobotChain: Int
): Long {
    if (lengthOfRobotChain == 0) {
        return buttonsToPress.size.toLong() // human will just press the buttons
    }

    val buttonSequence = buttonsToPress.toMutableList()
    buttonSequence.add(0, 'A') // robot arm always starts at 'A'
    return buttonSequence.windowed(2).sumOf { (fromButton, toButton) ->
        getLeastNumberOfInstructions(keypad, fromButton, toButton, lengthOfRobotChain)
    }
}

private val instructionSequenceLengthCache = mutableMapOf<Triple<Char, Char, Int>, Long>()
private fun getLeastNumberOfInstructions(
    keypad: Keypad,
    startButton: Char,
    targetButton: Char,
    lengthOfRobotChain: Int
): Long {
    instructionSequenceLengthCache[Triple(startButton, targetButton, lengthOfRobotChain)]?.let { return it }

    val numberOfInstructions = keypad.getValidInstructionsBetween(startButton, targetButton).minOf {
        val buttonsToPress = it + 'A' // need to end sequence with 'A'
        getLeastNumberOfInstructions(Keypad.DIRECTIONS, buttonsToPress, lengthOfRobotChain - 1)
    }
    instructionSequenceLengthCache[Triple(startButton, targetButton, lengthOfRobotChain)] = numberOfInstructions
    return numberOfInstructions
}


private class Keypad(buttonMatrix: List<List<Char>>) {

    companion object {
        const val NOT_A_BUTTON_LABEL = ' '

        val NUMBERS = Keypad(
            listOf(
                listOf('7', '8', '9'),
                listOf('4', '5', '6'),
                listOf('1', '2', '3'),
                listOf(' ', '0', 'A')
            )
        )
        val DIRECTIONS = Keypad(
            listOf(
                listOf(' ', '^', 'A'),
                listOf('<', 'v', '>'),
            )
        )
    }

    private val buttonCoordinates = buttonMatrix.flatMapIndexed { y, row ->
        row.mapIndexed { x, label -> Pair(label, Vector2(x, y)) }
    }

    private val buttonLabelToLocation: Map<Char, Vector2> =
        buttonCoordinates.filter { (label, _) -> label != NOT_A_BUTTON_LABEL }.associateBy({ it.first }) { it.second }

    private val prohibitedLocations: List<Vector2> =
        buttonCoordinates.filter { (label, _) -> label == NOT_A_BUTTON_LABEL }.map { (_, location) -> location }

    fun getValidInstructionsBetween(fromButtonLabel: Char, toButtonLabel: Char): List<List<Char>> {
        val fromButtonLocation =
            buttonLabelToLocation[fromButtonLabel] ?: throw RuntimeException("Invalid button: '$fromButtonLabel'")
        val toButtonLocation =
            buttonLabelToLocation[toButtonLabel] ?: throw RuntimeException("Invalid button: '$toButtonLabel'")

        val buttonDistance = toButtonLocation - fromButtonLocation
        val (noOfXInstructions, noOfYInstructions) = buttonDistance.abs()

        val xInstruction = if (buttonDistance.x < 0) '<' else '>'
        val xInstructions = List(noOfXInstructions.toInt()) { xInstruction }

        val yInstruction = if (buttonDistance.y < 0) '^' else 'v'
        val yInstructions = List(noOfYInstructions.toInt()) { yInstruction }

        // only use paths with one "corner" as going "zickzack" would result in to many instruction for the next robot in the chain
        return listOf((xInstructions + yInstructions), (yInstructions + xInstructions))
            .distinct()
            .filterNot { instructions -> crossesProhibitedLocation(fromButtonLabel, instructions) }
    }

    private fun crossesProhibitedLocation(startButtonLabel: Char, instructions: List<Char>): Boolean {
        val start =
            buttonLabelToLocation[startButtonLabel] ?: throw RuntimeException("Invalid button: '$startButtonLabel'")

        return instructions.map { instruction ->
            when (instruction) {
                '<' -> Vector2(-1, 0)
                '>' -> Vector2(1, 0)
                '^' -> Vector2(0, -1)
                'v' -> Vector2(0, 1)
                else -> throw RuntimeException("Invalid direction: '$instruction'")
            }
        }
            .runningFold(start) { currentLocation, instruction -> currentLocation + instruction }
            .any { visitedLocation -> visitedLocation in prohibitedLocations }
    }

}


