package aoc2022

import utils.split

object Day03 {

    fun part1(input: List<String>): Int {
        return input.map { it.split(it.length / 2) }
            .map { findCommonChar(it.first, it.second) }
            .sumOf { getValue(it) }
    }

    fun part2(input: List<String>): Int {
        return input.windowed(3, 3, false)
            .map { findCommonChar(it[0], it[1], it[2]) }
            .sumOf { getValue(it) }
    }
}

private fun findCommonChar(first: String, second: String, third: String = second): Char? {
    val firstArray = first.toCharArray().sortedArray()
    val secondArray = second.toCharArray().sortedArray()
    val thirdArray = third.toCharArray().sortedArray()

    for (c in firstArray) {
        if (secondArray.contains(c) && thirdArray.contains(c)) {
            return c
        }
    }
    return null
}

private fun getValue(c: Char?): Int {
    if (c == null) {
        return 0
    }

    val charValue = c.code
    return if (charValue >= 'a'.code) {
        charValue - 'a'.code + 1
    } else {
        charValue - 'A'.code + 27
    }
}