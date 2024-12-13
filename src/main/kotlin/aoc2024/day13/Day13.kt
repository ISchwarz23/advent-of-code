package aoc2024.day13

import utils.Vector2
import utils.split

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
            getCheapestSolution(parsePriceLocation(it[2], 10_000_000_000_000), parseButton(it[0]), parseButton(it[1])) ?: 0L
        }
    }

}

private fun getCheapestSolution(priceLocation: Vector2, buttonA: Button, buttonB: Button): Long? {

    var noOfButtonBPresses = (priceLocation.x / buttonB.movement.x) + 1
    var noOfButtonAPresses = ((priceLocation.y - noOfButtonBPresses * buttonB.movement.y) / buttonA.movement.y) + 1
    if (noOfButtonAPresses < 0) {
        noOfButtonAPresses = 0
    }

    while (noOfButtonBPresses >= 0) {
        val currentLocation = buttonA.movement * noOfButtonAPresses + buttonB.movement * noOfButtonBPresses
        if (currentLocation == priceLocation) {
            return noOfButtonAPresses * buttonA.cost + noOfButtonBPresses * buttonB.cost
        }
        if (currentLocation.x > priceLocation.x || currentLocation.y > priceLocation.y) {
            noOfButtonBPresses--
        } else {
            noOfButtonAPresses++
        }
    }

    return null
}

private fun parseButton(movement: String): Button {
    val result = "Button (\\w): X\\+(\\d+), Y\\+(\\d+)".toRegex().find(movement) ?: throw RuntimeException("Malformed button input")
    val cost = when(result.groupValues[1]) {
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
