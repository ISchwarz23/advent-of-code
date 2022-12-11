package aoc2022

object Day11 {

    fun part1(input: List<String>): Long {
        val monkeys = parseMonkeys(input)
        repeat(20) {
            executeRound(monkeys) { it / 3 }
        }
        return getLevelOfMonkeyBusiness(monkeys)
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseMonkeys(input)
        val commonDivider = monkeys.map { it.testDivider }.fold(1) { acc, next -> acc * next }
        repeat(10_000) {
            executeRound(monkeys) { it.mod(commonDivider).toLong() }
        }
        return getLevelOfMonkeyBusiness(monkeys)
    }
}

private fun executeRound(monkeys: List<Monkey>, handleWorryLevel: (Long) -> Long) {
    for (monkey in monkeys) {
        val inspectionResult = monkey.inspect(handleWorryLevel)
        inspectionResult.forEach { monkeys[it.second].items += it.first }
        monkey.items.clear()
    }
}

private fun getLevelOfMonkeyBusiness(monkeys: List<Monkey>): Long {
    return monkeys.map { it.inspections }.sortedDescending().take(2).fold(1) { acc, next -> acc * next }
}

private fun parseMonkey(input: List<String>): Monkey {
    return Monkey(
        input[0].substringBefore(":"),
        input[1].substringAfter(": ").split(", ").map { it.toLong() }.map { Item(it) }.toMutableList(),
        parseOperation(input[2]),
        parseTest(input[3]),
        parseTestDivider(input[3]),
        input[4].substringAfterLast(" ").toInt(),
        input[5].substringAfterLast(" ").toInt()
    )
}

private fun parseOperation(input: String): (Long) -> Long {
    val operation = input.substringAfter("old ")
    val operator = operation.substring(0, 1)
    val operand = operation.substring(2)
    return when (operator) {
        "+" -> { old: Long -> old + (if (operand == "old") old else operand.toLong()) }
        "-" -> { old: Long -> old - (if (operand == "old") old else operand.toLong()) }
        "*" -> { old: Long -> old * (if (operand == "old") old else operand.toLong()) }
        "/" -> { old: Long -> old / (if (operand == "old") old else operand.toLong()) }
        else -> error("Unknown operator: '$operator'")
    }
}

private fun parseTestDivider(input: String): Int {
    val test = input.substringAfter(": ")
    if (test.startsWith("divisible by ")) {
        return test.substringAfterLast(" ").toInt()
    } else {
        error("unknown test operation: '$test'")
    }
}

private fun parseTest(input: String): (Long) -> Boolean {
    val test = input.substringAfter(": ")
    if (test.startsWith("divisible by ")) {
        val divider = test.substringAfterLast(" ").toInt()
        return { value -> value.mod(divider) == 0 }
    } else {
        error("unknown test operation: '$test'")
    }
}

private fun parseMonkeys(input: List<String>): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    var index = 0
    while (input.lastIndex > index) {
        monkeys += parseMonkey(input.subList(index, index + 6))
        index += 7
    }
    return monkeys
}

data class Item(val worryLevel: Long)

data class Monkey(
    val name: String,
    val items: MutableList<Item>,
    val operation: (Long) -> Long,
    val testMethod: (Long) -> Boolean,
    val testDivider: Int,
    val throwTargetTrue: Int,
    val throwTargetFalse: Int
) {

    var inspections = 0L

    fun inspect(handleWorryLevel: (Long) -> Long): List<Pair<Item, Int>> {
        inspections += items.size
        return items.map { operation(it.worryLevel) }.map { handleWorryLevel(it) }
            .map { Pair(Item(it), getThrowTarget(it)) }
    }

    private fun getThrowTarget(worryLevel: Long): Int {
        return if (testMethod(worryLevel)) throwTargetTrue else throwTargetFalse
    }

}