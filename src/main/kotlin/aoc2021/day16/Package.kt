package aoc2021.day16


sealed interface Package {
    val version: Int
    val type: Int
    fun getValue(): Long

    data class Literal(
        override val version: Int,
        override val type: Int,
        val literal: Long
    ) : Package {

        override fun getValue(): Long = literal
        override fun toString(): String = "$literal"
    }

    data class Operator(
        override val version: Int,
        override val type: Int,
        val subPackages: List<Package>
    ) : Package {

        override fun getValue(): Long {
            return when (type) {
                0 -> subPackages.sumOf { it.getValue() }
                1 -> subPackages.fold(1) { acc, pack -> acc * pack.getValue() }
                2 -> subPackages.minOf { it.getValue() }
                3 -> subPackages.maxOf { it.getValue() }
                5 -> if (subPackages[0].getValue() > subPackages[1].getValue()) 1 else 0
                6 -> if (subPackages[0].getValue() < subPackages[1].getValue()) 1 else 0
                7 -> if (subPackages[0].getValue() == subPackages[1].getValue()) 1 else 0
                else -> throw RuntimeException("Unknown operator type")
            }
        }

        override fun toString(): String {
            return when (type) {
                0 -> subPackages.joinToString(" + ", "(", ")") { "" + it }
                1 -> subPackages.joinToString(" * ", "(", ")") { "" + it }
                2 -> subPackages.joinToString(", ", "min(", ")") { "" + it }
                3 -> subPackages.joinToString(", ", "max(", ")") { "" + it }
                5 -> "(${subPackages[0]} > ${subPackages[1]})"
                6 -> "(${subPackages[0]} < ${subPackages[1]})"
                7 -> "(${subPackages[0]} == ${subPackages[1]})"
                else -> throw RuntimeException("Unknown operator type")
            }
        }

    }


}