package aoc2024.day24

import utils.split

/**
 * My solution for day 24 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/24">AoC page</a>.
 */
object Day24 {

    fun part1(input: List<String>): Long {
        val (initialValues, logicGates) = parseInput(input)
        return runLogicGates(initialValues, logicGates)
    }

    fun part2(input: List<String>): String {
        val (_, logicGates) = parseInput(input)

        val highesZ = logicGates.map { it.outputName }.filter { it[0] == 'z' }.maxOf { it }

        val wrongOutputs = mutableSetOf<String>()

        for (gate in logicGates) {

            if (gate.outputName[0] == 'z' && gate.operator != LogicOperator.XOR && gate.outputName != highesZ) {
                wrongOutputs += gate.outputName
            }

            if (
                gate.operator == LogicOperator.XOR
                && gate.outputName[0] !in listOf('x', 'y', 'z')
                && gate.input1Name[0] !in listOf('x', 'y', 'z')
                && gate.input2Name[0] !in listOf('x', 'y', 'z')
            ) {
                wrongOutputs += gate.outputName
            }

            if (gate.operator == LogicOperator.AND && "x00" !in listOf(gate.input1Name, gate.input2Name)) {
                for (subGate in logicGates) {
                    if ((gate.outputName == subGate.input1Name || gate.outputName == subGate.input2Name) && subGate.operator != LogicOperator.OR)
                        wrongOutputs += gate.outputName
                }
            }

            if (gate.operator == LogicOperator.XOR) {
                for (subGate in logicGates) {
                    if ((gate.outputName == subGate.input1Name || gate.outputName == subGate.input2Name) && subGate.operator == LogicOperator.OR)
                        wrongOutputs += gate.outputName
                }
            }
        }

        return wrongOutputs.toList().sorted().joinToString(",")
    }

}

private fun getNumberStrFromOutputs(outputs: Map<String, Boolean>, numberName: Char): String {
    return outputs.entries.asSequence()
        .filter { (key, _) -> key[0] == numberName }
        .sortedByDescending { it.key }
        .map { it.value }
        .map { if (it) 1 else 0 }
        .joinToString("")
}

private fun getNumberFromOutputs(outputs: Map<String, Boolean>, numberName: Char): Long {
    return getNumberStrFromOutputs(outputs, numberName).toLong(2)
}

private fun parseInput(input: List<String>): Pair<MutableMap<String, Boolean>, List<LogicGate>> {
    val (givenValuesStr, logicGatesStr) = input.split { it.isEmpty() }

    val initialValues = givenValuesStr.map { it.split(": ") }
        .associate { (varName, value) -> varName to (value == "1") }

    val calculatedValues = initialValues.toMutableMap()
    val logicGates = logicGatesStr.map { it.split(" ") }
        .map { (input1, logicOperator, input2, _, output) ->
            val operator = LogicOperator.valueOf(logicOperator)
            LogicGate(input1, operator, input2, output)
        }
    return Pair(calculatedValues, logicGates)
}


private fun runLogicGates(
    initialValues: Map<String, Boolean>,
    logicGates: List<LogicGate>,
    rewiring: Map<String, String> = emptyMap()
): Long {

    val remainingLogicGates = logicGates.toMutableList()
    val values = initialValues.toMutableMap()

    var prevNumberOfRemainingGates = 0
    while (prevNumberOfRemainingGates != remainingLogicGates.size && remainingLogicGates.isNotEmpty()) {
        prevNumberOfRemainingGates = remainingLogicGates.size
        remainingLogicGates.toList().forEach { logicGate ->
            val result = logicGate.executeWithInput(values)
            if (result != null) {
                val (resultName, resultValue) = result
                val adaptedResultName = rewiring.getOrDefault(resultName, resultName)
                values[adaptedResultName] = resultValue
                remainingLogicGates -= logicGate
            }
        }
    }

    return values.entries.asSequence()
        .filter { (key, _) -> key[0] == 'z' }
        .sortedByDescending { it.key }
        .map { it.value }
        .map { if (it) 1 else 0 }
        .joinToString("")
        .toLong(2)
}

private class LogicGate(
    val input1Name: String,
    val operator: LogicOperator,
    val input2Name: String,
    val outputName: String
) {
    fun executeWithInput(values: Map<String, Boolean>): Pair<String, Boolean>? {
        val var1Value = values[input1Name] ?: return null
        val var2Value = values[input2Name] ?: return null
        val result = operator.apply(var1Value, var2Value)
        return Pair(outputName, result)
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
