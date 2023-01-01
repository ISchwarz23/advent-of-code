package aoc2022.day05

object Day05 {

    fun part1(stacks: List<ArrayDeque<Char>>, procedures: List<Procedure>): String {

        for (procedure in procedures) {
            repeat(procedure.numberOfItems) {
                stacks[procedure.targetStack - 1].add(stacks[procedure.originStack - 1].removeLast())
            }
        }
        return stacks.map { it.last() }.joinToString("")
    }

    fun part2(stacks: List<ArrayDeque<Char>>, procedures: List<Procedure>): String {

        for (procedure in procedures) {
            val index = stacks[procedure.originStack - 1].size - procedure.numberOfItems
            repeat(procedure.numberOfItems) {
                stacks[procedure.targetStack - 1].add(stacks[procedure.originStack - 1].removeAt(index))
            }
        }
        return stacks.map { it.last() }.joinToString("")
    }
}

data class Procedure(
    val numberOfItems: Int,
    val originStack: Int,
    val targetStack: Int
)
