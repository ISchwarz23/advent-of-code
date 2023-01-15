package aoc2022.day05

object Day05 {

    fun part1(stacks: List<ArrayDeque<Char>>, procedures: List<Procedure>): String {

        for (procedure in procedures) {
            repeat(procedure.numberOfItems) {
                stacks[procedure.targetStackIndex] += stacks[procedure.originStackIndex].removeLast()
            }
        }
        return stacks.map { it.last() }.joinToString("")
    }

    fun part2(stacks: List<ArrayDeque<Char>>, procedures: List<Procedure>): String {

        for (procedure in procedures) {
            val index = stacks[procedure.originStackIndex].size - procedure.numberOfItems
            repeat(procedure.numberOfItems) {
                stacks[procedure.targetStackIndex] += stacks[procedure.originStackIndex].removeAt(index)
            }
        }
        return stacks.map { it.last() }.joinToString("")
    }
}

