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

    private fun solve(input: List<String>, lengthOfRobotChain: Int): Long {
        return input.sumOf { code ->
            getNumericValue(code) * getNumberOfInstructions(Keypad.NUMBERS, code.toList(), lengthOfRobotChain)
        }
    }

}


private fun getNumericValue(code: String): Long {
    return code.filter { it < 'A' }.toLong()
}

private fun getNumberOfInstructions(
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
        getNumberOfInstructions(keypad, fromButton, toButton, lengthOfRobotChain)
    }
}

private val instructionSequenceLengthCache = mutableMapOf<Triple<Char, Char, Int>, Long>()
private fun getNumberOfInstructions(
    keypad: Keypad,
    startButton: Char,
    targetButton: Char,
    lengthOfRobotChain: Int
): Long {
    instructionSequenceLengthCache[Triple(startButton, targetButton, lengthOfRobotChain)]?.let { return it }

    val possibleInstructions = keypad.getPossibleInstructions(startButton, targetButton)
    val numberOfInstructions = possibleInstructions.minOf {
        val buttonsToPress = it + 'A' // need to end sequence with 'A'
        getNumberOfInstructions(Keypad.DIRECTIONS, buttonsToPress, lengthOfRobotChain - 1)
    }
    instructionSequenceLengthCache[Triple(startButton, targetButton, lengthOfRobotChain)] = numberOfInstructions
    return numberOfInstructions
}


private class Keypad(buttonMatrix: List<List<Char>>) {

    companion object {
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

    val buttons: Map<Char, Vector2> =
        buttonCoordinates.filter { (label, _) -> label != ' ' }.associateBy({ it.first }) { it.second }

    val prohibitedZones: List<Vector2> =
        buttonCoordinates.filter { (label, _) -> label == ' ' }.map { (_, coords) -> coords }

    fun getPossibleInstructions(fromButtonLabel: Char, toButtonLabel: Char): List<List<Char>> {
        val fromButton = buttons[fromButtonLabel] ?: throw RuntimeException("Invalid button: '$fromButtonLabel'")
        val toButton = buttons[toButtonLabel] ?: throw RuntimeException("Invalid button: '$toButtonLabel'")

        val delta = toButton - fromButton
        val distances = delta.abs()
        val xDirection = if (delta.x < 0) '<' else '>'
        val yDirection = if (delta.y < 0) '^' else 'v'

        return listOf(
            List(distances.x.toInt()) { xDirection } + List(distances.y.toInt()) { yDirection },
            List(distances.y.toInt()) { yDirection } + List(distances.x.toInt()) { xDirection },
        ).filter { !entersProhibitedArea(fromButtonLabel, it) }.distinct()
    }

    private fun entersProhibitedArea(startButtonLabel: Char, path: List<Char>): Boolean {
        val start = buttons[startButtonLabel] ?: throw RuntimeException("Invalid button: '$startButtonLabel'")
        if (start in prohibitedZones) {
            return true
        }

        val directions = path.map {
            when (it) {
                '<' -> Vector2(-1, 0)
                '>' -> Vector2(1, 0)
                '^' -> Vector2(0, -1)
                'v' -> Vector2(0, 1)
                else -> throw RuntimeException("Invalid direction: '$it'")
            }
        }

        var currentLocation = start
        for (direction in directions) {
            currentLocation += direction
            if (currentLocation in prohibitedZones) {
                return true
            }
        }
        return false
    }

}

