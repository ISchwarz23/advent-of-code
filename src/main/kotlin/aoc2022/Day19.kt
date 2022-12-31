package aoc2022

object Day19 {

    fun part1(input: List<String>): Int {
        return input.map { Blueprint.of(it) }.sumOf { getMaxNumberOfGeodes(it, 24) * it.id }
    }

    fun part2(input: List<String>): Int {
        return input.take(3).map { Blueprint.of(it) }
            .map { getMaxNumberOfGeodes(it, 32) }
            .reduce(Int::times)
    }
}

private fun getMaxNumberOfGeodes(
    blueprint: Blueprint,
    minutesRemaining: Int,
    robots: RobotFleet = RobotFleet(numberOfOreRobots = 1),
    budget: Budget = Budget(),
    intermediateMax: Int = 0
): Int {
    // finish if in last minute
    if (minutesRemaining == 0) return budget.geode

    // check if it could beat intermediate max
    val assumedMax = budget.geode + (0 until minutesRemaining).sumOf { it + robots.numberOfGeodeRobots }
    if (assumedMax <= intermediateMax) return 0

    // prepare
    val collectedEarths = robots.collectEarths()
    val budgetOneMinuteBefore = budget - collectedEarths
    val results = mutableListOf<Int>()

    // do not build robot
    results += getMaxNumberOfGeodes(blueprint, minutesRemaining - 1, robots, budget + collectedEarths, intermediateMax)

    // build robots
    for (pricePerRobot in blueprint.pricePerRobotType) {
        val price = pricePerRobot.second
        val newRobot = pricePerRobot.first
        if (couldBuild(price, budget) && !couldBuild(price, budgetOneMinuteBefore)) {
            val newBudget = budget + collectedEarths - price
            val newRobots = robots + newRobot
            results += getMaxNumberOfGeodes(
                blueprint,
                minutesRemaining - 1,
                newRobots,
                newBudget,
                results.maxOf { it })
        }
    }

    // return best result
    return results.maxOf { it }
}

private fun couldBuild(price: Budget, budget: Budget): Boolean {
    return price.ore <= budget.ore
            && price.clay <= budget.clay
            && price.obsidian <= budget.obsidian
            && price.geode <= budget.geode
}

private data class RobotFleet(
    val numberOfOreRobots: Int = 0,
    val numberOfClayRobots: Int = 0,
    val numberOfObsidianRobots: Int = 0,
    val numberOfGeodeRobots: Int = 0
) {

    fun collectEarths(): Budget {
        return Budget(numberOfOreRobots, numberOfClayRobots, numberOfObsidianRobots, numberOfGeodeRobots)
    }

    operator fun plus(other: RobotFleet): RobotFleet {
        return RobotFleet(
            this.numberOfOreRobots + other.numberOfOreRobots,
            this.numberOfClayRobots + other.numberOfClayRobots,
            this.numberOfObsidianRobots + other.numberOfObsidianRobots,
            this.numberOfGeodeRobots + other.numberOfGeodeRobots
        )
    }

}

private data class Budget(
    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geode: Int = 0
) {

    operator fun plus(other: Budget): Budget {
        return Budget(
            this.ore + other.ore,
            this.clay + other.clay,
            this.obsidian + other.obsidian,
            this.geode + other.geode
        )
    }

    operator fun minus(other: Budget): Budget {
        return Budget(
            this.ore - other.ore,
            this.clay - other.clay,
            this.obsidian - other.obsidian,
            this.geode - other.geode
        )
    }

}

private data class Blueprint(
    val id: Int,
    val costOreRobot: Budget,
    val costClayRobot: Budget,
    val costObsidianRobot: Budget,
    val costGeodeRobot: Budget
) {

    val pricePerRobotType: List<Pair<RobotFleet, Budget>> by lazy {
        listOf(
            Pair(RobotFleet(numberOfGeodeRobots = 1), costGeodeRobot),
            Pair(RobotFleet(numberOfObsidianRobots = 1), costObsidianRobot),
            Pair(RobotFleet(numberOfClayRobots = 1), costClayRobot),
            Pair(RobotFleet(numberOfOreRobots = 1), costOreRobot),
        )
    }

    companion object {

        fun of(bluePrintStr: String): Blueprint {
            val id = bluePrintStr.substringAfter(" ").substringBefore(":").toInt()
            val oreRobotCost =
                Budget(ore = bluePrintStr.substringAfter("ore robot costs ").substringBefore(" ore").toInt())
            val clayRobotCost =
                Budget(ore = bluePrintStr.substringAfter("clay robot costs ").substringBefore(" ore").toInt())
            val obsidianRobotCost = Budget(
                ore = bluePrintStr.substringAfter("obsidian robot costs ").substringBefore(" ore").toInt(),
                clay = bluePrintStr.substringAfter("ore and ").substringBefore(" clay").toInt()
            )
            val geodeRobotCost = Budget(
                ore = bluePrintStr.substringAfter("geode robot costs ").substringBefore(" ore").toInt(),
                obsidian = bluePrintStr.substringAfter("geode robot costs ").substringAfter("ore and ")
                    .substringBefore(" obsidian").toInt(),
            )
            return Blueprint(id, oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost)
        }
    }

}

