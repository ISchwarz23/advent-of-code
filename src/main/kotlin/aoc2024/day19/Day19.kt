package aoc2024.day19

/**
 * My solution for day 19 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/19">AoC page</a>.
 */
object Day19 {

    fun part1(input: List<String>): Int {
        val availableTowels = input[0].split(", ")
        val designsToDisplay = input.subList(2, input.size)

        return designsToDisplay.count { getNumberOfPossibilities(it, availableTowels) > 0 }
    }

    fun part2(input: List<String>): Long {
        val availableTowels = input[0].split(", ")
        val designsToDisplay = input.subList(2, input.size)

        return designsToDisplay.sumOf { getNumberOfPossibilities(it, availableTowels) }
    }

}

private fun getNumberOfPossibilities(pattern: String, availableTowels: List<String>): Long {

    val subPatterns = mutableMapOf(pattern to 1L)
    var noOfSolutions = 0L

    while (subPatterns.isNotEmpty()) {
        subPatterns.keys.toList().forEach { subPattern ->
            val noOfSubOptions = subPatterns.remove(subPattern)!!
            availableTowels
                .filter { towel -> subPattern.startsWith(towel) }
                .forEach { towel ->
                    subPatterns.compute(subPattern.substring(towel.length)) { _, number -> (number ?: 0L) + noOfSubOptions }
                }
        }
        noOfSolutions += subPatterns.remove("") ?: 0L
    }

    return noOfSolutions
}
