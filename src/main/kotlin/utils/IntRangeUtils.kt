package utils

infix fun IntRange.contains(other: IntRange): Boolean {
    return this.contains(other.first) && this.contains(other.last)
}

infix fun IntRange.overlaps(other: IntRange): Boolean {
    return this.contains(other.first) || this.contains(other.last) || other.contains(this.first) || other.contains(this.last)
}

fun IntRange.increase(spaceToIncrease: Int): IntRange {
    return (this.first-spaceToIncrease)..(this.last+spaceToIncrease)
}

fun IntRange.increaseInsideRange(padding: Int = 1, range: IntRange = 0..Int.MAX_VALUE): IntRange {
    val newFirst = if (this.first - padding > range.first) this.first - padding else range.first
    val newLast = if (this.last + padding < range.last) this.last + padding else range.last
    return newFirst..newLast
}