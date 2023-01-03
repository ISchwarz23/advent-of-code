package aoc2021.day17

import utils.calcPartialSum
import kotlin.math.absoluteValue

object Day17 {

    fun part1(targetArea: TargetArea, launcherPosition: Position = Position(0, 0)): Int {
        return getMaximumVelocityY(targetArea, launcherPosition).calcPartialSum()
    }

    fun part2(targetArea: TargetArea, launcherPosition: Position = Position(0, 0)): Int {

        // REMARK:
        // currently the calculation is only working for target areas that are located to the right of
        // and below the launcher position

        // calculate range for possible vX values
        val deltaXStart = targetArea.xStart - launcherPosition.x
        val velocityMaxX = targetArea.xEnd - launcherPosition.x
        var velocityMinX = 0
        while (velocityMinX.calcPartialSum() < deltaXStart) {
            velocityMinX++
        }

        // calculate range for possible vY values
        val velocityMaxY = getMaximumVelocityY(targetArea, launcherPosition)
        val velocityMinY = targetArea.yStart - launcherPosition.y

        // find velocities
        val velocities = mutableListOf<Velocity>()
        for (velocityX in velocityMinX..velocityMaxX) {
            for (velocityY in velocityMinY..velocityMaxY) {
                val velocity = Velocity(velocityX, velocityY)
                if (hitsTarget(targetArea, launcherPosition, velocity)) {
                    velocities += velocity
                }
            }
        }
        return velocities.size
    }
}

private fun getMaximumVelocityY(targetArea: TargetArea, launcherPosition: Position): Int {
    val velocityY = if (launcherPosition.y > targetArea.yEnd) {
        // if we launch above the target area, we target the
        // lower edge of the area
        (launcherPosition.y - targetArea.yStart).absoluteValue - 1
    } else if (launcherPosition.y < targetArea.yStart) {
        // if we launch below the target area, we target the
        // upper edge of the area
        (targetArea.yEnd - launcherPosition.y).absoluteValue
    } else {
        // we launch at the height of the target area, so we
        // can shoot as high as we want
        return Int.MAX_VALUE
    }
    return velocityY
}

private fun hitsTarget(targetArea: TargetArea, launcherPosition: Position, initialVelocity: Velocity): Boolean {

    var position = launcherPosition
    var velocity = initialVelocity

    while (position.y >= targetArea.yStart || velocity.vY > 0) {
        if (targetArea.contains(position)) {
            return true
        }
        position += velocity
        velocity = velocity.decrease()
    }
    return false
}
