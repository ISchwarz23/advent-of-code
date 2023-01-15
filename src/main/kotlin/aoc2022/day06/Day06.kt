package aoc2022.day06

import utils.allCharsUnique


object Day06 {

    fun part1(input: String): Int {
        return getIndexOfStartSequence(input, 4)
    }

    fun part2(input: String): Int {
        return getIndexOfStartSequence(input, 14)
    }
}

private fun getIndexOfStartSequence(message: String, startSequenceLength: Int): Int {
    return message.windowed(startSequenceLength)
        .takeWhile { it.allCharsUnique.not() }
        .count() + startSequenceLength
}