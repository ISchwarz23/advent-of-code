package aoc2021.day14

object Day14 {

    fun part1(template: String, insertionRules: List<InsertionRule>): Long {
        return PolymerizationEquipment(insertionRules).runPolymerization(template, 10)
    }

    fun part2(template: String, insertionRules: List<InsertionRule>): Long {
        return PolymerizationEquipment(insertionRules).runPolymerization(template, 40)
    }

}

