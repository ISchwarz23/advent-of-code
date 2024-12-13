package aoc2024.day13

import utils.Vector2
import utils.split
import kotlin.math.abs

/**
 * My solution for day 13 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/13">AoC page</a>.
 */
object Day13 {

    fun part1(input: List<String>): Long {
        return input.split { it.isEmpty() }.sumOf {
            getCheapestSolution(parsePriceLocation(it[2]), parseButton(it[0]), parseButton(it[1])) ?: 0L
        }
    }

    fun part2(input: List<String>): Long {
        return input.split { it.isEmpty() }.sumOf {
            getCheapestSolution(parsePriceLocation(it[2], 10_000_000_000_000), parseButton(it[0]), parseButton(it[1]))
                ?: 0L
        }
    }

}

private fun getCheapestSolution(priceLocation: Vector2, buttonA: Button, buttonB: Button): Long? {

    // solve LGS
    // buttonA.movement.x * A + buttonB.movement.x * B = priceLocation.x
    // buttonA.movement.y * A + buttonB.movement.y * B = priceLocation.y
    val a1 = buttonA.movement.x
    val b1 = buttonB.movement.x
    val c1 = priceLocation.x
    val a2 = buttonA.movement.y
    val b2 = buttonB.movement.y
    val c2 = priceLocation.y

    // Calculate the determinant of the coefficient matrix
    val determinant = a1 * b2 - a2 * b1

    // If determinant is zero, the system has no unique solution (either infinite or none)
    if (abs(determinant).toInt() == 0) {
        return null
    }

    // Using Cramer's Rule to solve for x and y
    val x = (c1 * b2 - c2 * b1) / determinant
    val y = (a1 * c2 - a2 * c1) / determinant

    // double check as integer division is done before
    return if (buttonA.movement * x + buttonB.movement * y == priceLocation) {
        x * buttonA.cost + y * buttonB.cost
    } else {
        null
    }
}

private fun parseButton(movement: String): Button {
    val result = "Button (\\w): X\\+(\\d+), Y\\+(\\d+)".toRegex().find(movement)
        ?: throw RuntimeException("Malformed button input")
    val cost = when (result.groupValues[1]) {
        "A" -> 3L
        "B" -> 1L
        else -> throw RuntimeException("Unknown button: ${result.groupValues[1]}")
    }
    val x = result.groupValues[2].toInt()
    val y = result.groupValues[3].toInt()
    return Button(Vector2(x, y), cost)
}

private fun parsePriceLocation(price: String, offset: Long = 0): Vector2 {
    val result = "Prize: X=(\\d+), Y=(\\d+)".toRegex().find(price) ?: throw RuntimeException("Malformed prize input")
    val x = result.groupValues[1].toLong() + offset
    val y = result.groupValues[2].toLong() + offset
    return Vector2(x, y)
}

private data class MachineState(val position: Vector2, val totalCost: Long)

private data class Button(val movement: Vector2, val cost: Long)
