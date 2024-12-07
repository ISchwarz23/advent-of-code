package aoc2024.day07

/**
 * My solution for day 7 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/7">AoC page</a>.
 */
object Day07 {

    enum class Operation(val apply: (first: Long?, second: Int) -> Long) {
        ADD({ first, second -> (first ?: 0) + second }),
        MULTIPLY({ first, second -> (first ?: 1) * second }),
        CONCAT({ first, second -> "${first ?: ""}$second".toLong() })
    }

    fun part1(input: List<String>): Long {
        return solve(input, listOf(Operation.ADD, Operation.MULTIPLY))
    }

    fun part2(input: List<String>): Long {
        return solve(input, listOf(Operation.ADD, Operation.MULTIPLY, Operation.CONCAT))
    }

    private fun solve(input: List<String>, operationsToEvaluate: List<Operation>): Long {
        val equations = input.map { it.split(": ") }.map { Pair(it[0].toLong(), it[1].split(" ").map { it.toInt() }) }

        var sum = 0L
        for (equation in equations) {
            var possibleResults = listOf<Long>()

            equation.second.forEach { currentNumber ->
                val possibleResultsCopy = possibleResults.toList()

                possibleResults = operationsToEvaluate.flatMap { operation ->
                    if (possibleResults.isEmpty()) {
                        listOf(operation.apply(null, currentNumber))
                    } else {
                        possibleResultsCopy.map { operation.apply(it, currentNumber) }
                    }
                }
            }

            if (possibleResults.contains(equation.first)) {
                sum += equation.first
            }
        }
        return sum
    }

}
