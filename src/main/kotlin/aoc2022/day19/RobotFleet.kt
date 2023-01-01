package aoc2022.day19

data class RobotFleet(
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