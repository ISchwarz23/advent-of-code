package aoc2024.day21

import utils.Vector2

/**
 * My solution for day 21 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/21">AoC page</a>.
 */
object Day21 {

    private val numericKeypad = Keypad(listOf(
        listOf('7', '8', '9'),
        listOf('4', '5', '6'),
        listOf('1', '2', '3'),
        listOf(' ', '0', 'A')
    ))
    private val directionKeypad = Keypad(listOf(
        listOf(' ', '^', 'A'),
        listOf('<', 'v', '>'),
    ))

    fun part1(input: List<String>): Long {
        return solve(input, 3)
    }

    fun part2(input: List<String>): Long {
        return solve(input, 26)
    }

    private fun solve(input: List<String>, lengthOfRobotChain: Int): Long {
        return input.sumOf { requiredInput ->
            val instructionSequence = getSequenceOfButtonPresses(requiredInput.toList(), lengthOfRobotChain, numericKeypad)
            val numericValue = requiredInput.filter { it < 'A' }.toLong()
            val complexity = numericValue * instructionSequence.size
            println("$requiredInput: ${instructionSequence.size} * $numericValue = $complexity")
            complexity
        }
    }

    private fun getSequenceOfButtonPresses(
        input: List<Char>,
        lengthOfRobotChain: Int,
        keypad: Keypad
    ): List<Char> {
        if (lengthOfRobotChain == 0) {
            return input
        }

        val requiredInput = input.toMutableList()
        requiredInput.add(0, 'A')
        val result = requiredInput.windowed(2).flatMap { (from, to) ->
            val validShortestPaths = keypad.getValidShortestPaths(from, to)
            val result = validShortestPaths
                .map { getSequenceOfButtonPresses(it + 'A', lengthOfRobotChain - 1, directionKeypad) }
                .minByOrNull { it.size }!!
            result
        }

        return result
    }

}


// private data class KeypadButton(val position: Vector2, val label: Char)

private class Keypad(buttonMatrix: List<List<Char>>) {

    private val buttonCoordinates = buttonMatrix.flatMapIndexed { y, row ->
        row.mapIndexed { x, label -> Pair(label, Vector2(x, y)) }
    }

    val buttons: Map<Char, Vector2> = buttonCoordinates.filter { (label, _) -> label != ' ' }.associateBy({ it.first }) { it.second }

    val prohibitedZones: List<Vector2> = buttonCoordinates.filter { (label, _) -> label == ' ' }.map { (_, coords) -> coords }

    fun getValidShortestPaths(fromButtonLabel: Char, toButtonLabel: Char): List<List<Char>> {
        val fromButton = buttons[fromButtonLabel] ?: throw RuntimeException("Invalid button: '$fromButtonLabel'")
        val toButton = buttons[toButtonLabel] ?: throw RuntimeException("Invalid button: '$toButtonLabel'")

        val delta = toButton - fromButton
        val distances = delta.abs()
        val xDirection = if(delta.x < 0) '<' else '>'
        val yDirection = if(delta.y < 0) '^' else 'v'

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

        val directions = path.map { when(it) {
            '<' -> Vector2(-1, 0)
            '>' -> Vector2(1, 0)
            '^' -> Vector2(0, -1)
            'v' -> Vector2(0, 1)
            else -> throw RuntimeException("Invalid direction: '$it'")
        } }

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


