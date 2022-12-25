package utils

class DoubleRange(override val start: Double, override val endInclusive: Double) : ClosedFloatingPointRange<Double> {
    override fun lessThanOrEquals(a: Double, b: Double): Boolean = a <= b
}

class MutableDoubleRange(override var start: Double, override var endInclusive: Double) : ClosedFloatingPointRange<Double> {
    override fun lessThanOrEquals(a: Double, b: Double): Boolean = a <= b
    fun copy(): MutableDoubleRange = MutableDoubleRange(start, endInclusive)
}