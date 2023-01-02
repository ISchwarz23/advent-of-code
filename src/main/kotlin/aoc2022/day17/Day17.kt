package aoc2022.day17

import utils.Vector2

object Day17 {

    fun part1(horizontalMoves: List<Vector2>): Long {
        return simulateRocks(horizontalMoves, 2022).fillHeight
    }

    fun part2(horizontalMoves: List<Vector2>): Long {
        return simulateRocks(horizontalMoves, 1_000_000_000_000).fillHeight
    }
}


private val rockTemplates = arrayListOf(
    Rock(listOf(Vector2(0, 0), Vector2(1, 0), Vector2(2, 0), Vector2(3, 0))),
    Rock(listOf(Vector2(1, 2), Vector2(0, 1), Vector2(1, 1), Vector2(2, 1), Vector2(1, 0))),
    Rock(listOf(Vector2(2, 2), Vector2(2, 1), Vector2(0, 0), Vector2(1, 0), Vector2(2, 0))),
    Rock(listOf(Vector2(0, 3), Vector2(0, 2), Vector2(0, 1), Vector2(0, 0))),
    Rock(listOf(Vector2(0, 1), Vector2(1, 1), Vector2(0, 0), Vector2(1, 0))),
)

private fun simulateRocks(horizontalMoves: List<Vector2>, numberOfRocks: Long): Cave {
    val cave = Cave(horizontalMoves, rockTemplates, 7)
    var repeatingStartFillHeight: Long? = null
    var repeatingStartRockNumber: Long? = null
    var repeatingStartWindIndex: Int? = null

    var i = 0L
    while (i < numberOfRocks) {

        // check if anticipation could be applied, by repeating the existing pattern
        val repeating = i > horizontalMoves.size
        val repeatingPattern = cave.windIndex % rockTemplates.size == cave.rockIndex
        if (repeating && repeatingPattern) {
            if (repeatingStartFillHeight == null) {
                repeatingStartFillHeight = cave.fillHeight
                repeatingStartRockNumber = i
                repeatingStartWindIndex = cave.windIndex
            } else if (cave.windIndex == repeatingStartWindIndex) {
                val deltaY = cave.fillHeight - repeatingStartFillHeight
                val deltaRocks = i - repeatingStartRockNumber!!

                val requiredRepetitions = (numberOfRocks - i) / deltaRocks
                if (requiredRepetitions > 0) {
                    i += deltaRocks * requiredRepetitions
                    val newFill = cave.fill.map { it plusY (deltaY * requiredRepetitions) }
                    cave.fill.clear()
                    cave.fill += newFill
                }

                repeatingStartFillHeight = null
            }
        }


        // drop new rock
        var finished = false
        var rockPosition = cave.getNextRockToFall()
        var potentialRockPosition: Rock

        // plain simulation
        do {
            // try to move rock horizontally
            potentialRockPosition = rockPosition + cave.getNextWind()
            if (potentialRockPosition.startX >= 0 && potentialRockPosition.endX < cave.width &&
                cave.fill.none { potentialRockPosition.points.contains(it) }
            ) {
                rockPosition = potentialRockPosition
            }

            // move rock down or finish
            potentialRockPosition = rockPosition.moveDown()
            if (potentialRockPosition.heightAboveGround < 0 || cave.fill.any { potentialRockPosition.points.contains(it) }
            ) {
                cave += rockPosition
                finished = true
            } else {
                rockPosition = potentialRockPosition
            }

        } while (!finished)

        i++
    }
    return cave
}


private class Cave(
    private val winds: List<Vector2>,
    private val rockTemplates: List<Rock>,
    val width: Int
) {

    var windIndex = -1
    var rockIndex = -1

    val fill = mutableListOf<Vector2>()
    val fillHeight: Long
        get() {
            val fillHeight = fill.maxOfOrNull { it.y }
            return if (fillHeight == null) 0 else fillHeight + 1
        }

    fun getNextWind(): Vector2 {
        windIndex++
        if (windIndex >= winds.size) windIndex = 0
        return winds[windIndex]
    }

    fun getNextRockToFall(): Rock {
        rockIndex++
        if (rockIndex >= rockTemplates.size) rockIndex = 0
        return ((rockTemplates[rockIndex] plusX 2) plusY fillHeight + 3)
    }

    operator fun plusAssign(rock: Rock) {
        rock.points.forEach { fill += it }
    }
}

private data class Rock(val points: List<Vector2>) {

    val heightAboveGround: Long by lazy { points.minOf { it.y } }
    val startX: Long by lazy { points.minOf { it.x } }
    val endX: Long by lazy { points.maxOf { it.x } }

    infix fun plusY(y: Long): Rock {
        return Rock(points.map { it plusY y })
    }

    infix fun plusX(x: Long): Rock {
        return Rock(points.map { it plusX x })
    }

    operator fun plus(vector2: Vector2): Rock {
        return Rock(points.map { it + vector2 })
    }

    fun moveDown(): Rock {
        return Rock(points.map { it minusY 1 })
    }

}