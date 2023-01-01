package aoc2022.day19

object Day19 {
    fun part1(blueprints: List<Blueprint>): Int {
        return blueprints.sumOf { getMaxNumberOfGeodes(it, 24) * it.id }
    }

    fun part2(blueprints: List<Blueprint>): Int {
        return blueprints.take(3)
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
