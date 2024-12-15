package utils


data class Rect(val xRange: LongRange, val yRange: LongRange) {

    val width: Long = xRange.last - xRange.first + 1
    val height: Long = yRange.last - yRange.first + 1

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

    constructor(width: Int, height: Int) : this(width.toLong(),height.toLong())

    constructor(width: Long, height: Long) : this(0, width, 0, height)

    operator fun contains(other: Vector2): Boolean {
        return other.x in this.xRange && other.y in this.yRange
    }

    infix fun overlaps(other: Rect): Boolean {
        return other.xRange overlaps this.xRange && other.yRange overlaps  this.yRange
    }

    fun move(movement: Vector2): Rect {
        return Rect(this.xRange.first + movement.x, this.width, this.yRange.first + movement.y, this.height)
    }

}