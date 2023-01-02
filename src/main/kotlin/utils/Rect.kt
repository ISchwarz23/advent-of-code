package utils

data class Rect(val xRange: LongRange, val yRange: LongRange) {

    constructor(x: IntRange, y: IntRange) : this(
        x.first.toLong()..x.last,
        y.first.toLong()..y.last
    )

    constructor(xStart: Long, width: Long, yStart: Long, height: Long) : this(
        xStart until xStart + width,
        yStart until yStart + height
    )

    constructor(xStart: Int, width: Int, yStart: Int, height: Int) : this(
        xStart.toLong(), width.toLong(),
        yStart.toLong(), height.toLong()
    )

    operator fun contains(other: Vector2): Boolean {
        return other.x in this.xRange && other.y in this.yRange
    }

}