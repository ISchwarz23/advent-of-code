package aoc2022.day19

data class Budget(
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