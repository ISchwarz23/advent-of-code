package aoc2023.day09

/**
 * My solution for day 9 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/9">AoC page</a>.
 */
object Day09 {

    fun part1(input: List<List<Int>>): Int {
        return input.sumOf { numberSequence -> calculateNextNumberInSequence(numberSequence) }
    }

    fun part2(input: List<List<Int>>): Int {
        return input.sumOf { numberSequence -> calculatePreviousNumberInSequence(numberSequence) }
    }

}


fun calculateNextNumberInSequence(numberSequence: List<Int>): Int {
    if(numberSequence.all { number -> number == 0 }) return 0

    val deltaNumberSequence = numberSequence.windowed(2).map { (first, second) -> second - first }
    return numberSequence.last() + calculateNextNumberInSequence(deltaNumberSequence)
}


fun calculatePreviousNumberInSequence(numberSequence: List<Int>): Int {
    if(numberSequence.all { number -> number == 0 }) return 0

    val deltaNumberSequence = numberSequence.windowed(2).map { (first, second) -> second - first }
    return numberSequence.first() - calculatePreviousNumberInSequence(deltaNumberSequence)
}
