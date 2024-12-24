package aoc2024.day24

import utils.pow
import utils.split

/**
 * My solution for day 24 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/24">AoC page</a>.
 */
object Day24 {

    fun part1(input: List<String>): Long {
        val (givenValuesStr, logicGatesStr) = input.split { it.isEmpty() }

        val calculatedValues = givenValuesStr.map { it.split(": ") }
            .associate { (varName, value) -> varName to (value == "1") }
            .toMutableMap()

        val logicGates = logicGatesStr.map { it.split(" -> ") }
            .map { (logicOperation, resultName) ->
                val (var1, logicOperator, var2) = logicOperation.split(" ")
                val oper = LogicOperator.valueOf(logicOperator)
                LogicGate(var1, oper, var2, resultName)
            }
            .toMutableList()

        while (logicGates.isNotEmpty()) {
            logicGates.toList().forEach { logicGate ->
                val result = logicGate.executeWithInput(calculatedValues)
                if (result != null) {
                    calculatedValues[result.first] = result.second
                    logicGates -= logicGate
                }
            }
        }

        return calculatedValues.entries.asSequence()
            .filter { (key, _) -> key[0] == 'z' }
            .sortedByDescending { it.key }
            .map { it.value }
            .map { if (it) 1L else 0L }
            .fold("") { acc, next -> acc + next }
            .toLong(2)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

}

private class LogicGate(
    private val var1Name: String,
    private val operator: LogicOperator,
    private val var2Name: String,
    private val resultName: String
) {
    fun executeWithInput(values: Map<String, Boolean>): Pair<String, Boolean>? {
        val var1Value = values[var1Name] ?: return null
        val var2Value = values[var2Name] ?: return null
        val result = operator.apply(var1Value, var2Value)
        return Pair(resultName, result)
    }

}

private enum class LogicOperator(private val logicFunction: (first: Boolean, second: Boolean) -> Boolean) {
    AND({ first, second -> first && second }),
    OR({ first, second -> first || second }),
    XOR({ first, second -> first != second });

    fun apply(first: Boolean, second: Boolean): Boolean {
        return logicFunction(first, second)
    }

}
