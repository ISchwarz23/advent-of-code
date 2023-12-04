package aoc2023.day03

import utils.increase
import utils.increaseInsideRange
import utils.overlaps

data class Bounds(
    val xBounds: IntRange,
    val yBounds: IntRange
) {

    fun increaseInsideBounds(
        padding: Int = 1,
        xBounds: IntRange = 0..Int.MAX_VALUE,
        yBounds: IntRange = 0..Int.MAX_VALUE
    ): Bounds {
        return Bounds(
            this.xBounds.increaseInsideRange(padding, xBounds),
            this.yBounds.increaseInsideRange(padding, yBounds)
        )
    }

    fun increase(
        padding: Int = 1
    ): Bounds {
        return Bounds(
            this.xBounds.increase(padding),
            this.yBounds.increase(padding)
        )
    }

    infix fun overlaps(other: Bounds): Boolean {
        return this.xBounds.overlaps(other.xBounds) && this.yBounds.overlaps(other.yBounds)
    }

}