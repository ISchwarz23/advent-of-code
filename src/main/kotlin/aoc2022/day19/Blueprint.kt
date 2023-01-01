package aoc2022.day19

data class Blueprint(
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