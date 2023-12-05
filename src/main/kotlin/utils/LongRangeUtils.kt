package utils

infix fun LongRange.contains(other: LongRange): Boolean {
    return this.contains(other.first) && this.contains(other.last)
}

infix fun LongRange.overlaps(other: LongRange): Boolean {
    return this.contains(other.first) || this.contains(other.last) || other.contains(this.first) || other.contains(this.last)
}

fun LongRange.increase(padding: Int): LongRange {
    return (this.first-padding)..(this.last+padding)
}
