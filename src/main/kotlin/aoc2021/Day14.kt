package aoc2021

object Day14 {

    fun part1(template: String, insertionRules: List<InsertionRule>): Long {
        return PolymerizationEquipment(insertionRules).runPolymerization(template, 10)
    }

    fun part2(template: String, insertionRules: List<InsertionRule>): Long {
        return PolymerizationEquipment(insertionRules).runPolymerization(template, 40)
    }

}

data class InsertionRule(val pattern: String, val insertion: String)

private class PolymerizationEquipment(insertionRules: List<InsertionRule>) {

    private val rules = insertionRules.groupBy { it.pattern }.mapValues { it.value[0] }

    fun runPolymerization(template: String, numberOfSteps: Int = 1): Long {

        // get counts of each two-char-junks
        val junkCounts = template.windowed(2)
            .mapIndexed { index, value -> value to index }
            .groupBy({ pair -> pair.first }) { it.second }
            .mapValues { it.value.size.toLong() }
            .toMutableMap()

        // do requested step repetition
        repeat(numberOfSteps) {
            // copy junks with its counts
            val junkWithCount = junkCounts.entries.toList()
            // reset junk count
            junkCounts.clear()
            // do polymerization
            junkWithCount.forEach {
                // get matching rule
                val rule = rules[it.key]
                if (rule != null) {
                    // create the two resulting two-char-junks
                    val first = it.key[0] + rule.insertion
                    val second = rule.insertion + it.key[1]
                    // add the count of the new junks
                    junkCounts[first] = junkCounts.getOrDefault(first, 0) + it.value
                    junkCounts[second] = junkCounts.getOrDefault(second, 0) + it.value
                }
            }
        }

        // get the number of occurrence for each char
        val elementCounts = junkCounts.entries
            .map { Pair(it.key[1], it.value) }
            .groupBy({ pair -> pair.first }) { it.second }
            .mapValues { it.value.sum() }
            .toMutableMap()
        elementCounts[template[0]] = elementCounts.getOrDefault(template[0], 0) + 1

        // find elements with min and max occurrence
        val maxElementCount = elementCounts.entries.maxByOrNull { it.value }?.value ?: 0
        val minElementCount = elementCounts.entries.minByOrNull { it.value }?.value ?: 0
        // calculate result (difference)
        return maxElementCount - minElementCount
    }

}

