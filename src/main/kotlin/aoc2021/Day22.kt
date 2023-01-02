package aoc2021

import kotlin.math.max
import kotlin.math.min

object Day22 {

    fun part1(rebootSteps: List<RebootStep>): Long {
        val reactor = Reactor(-50..50, -50..50, -50..50)
        rebootSteps.forEach { reactor.apply(it) }
        return reactor.numberOfActivatedCubes
    }

    fun part2(rebootSteps: List<RebootStep>): Long {
        val reactor = Reactor()
        rebootSteps.forEach { reactor.apply(it) }
        return reactor.numberOfActivatedCubes
    }

}

class Reactor(
    xSize: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
    ySize: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
    zSize: IntRange = Int.MIN_VALUE..Int.MAX_VALUE
) {
    val numberOfActivatedCubes: Long
        get() = activatedCubesClusters.sumOf { it.size }

    private val range3D = IntRange3D(xSize, ySize, zSize)
    private var activatedCubesClusters = listOf<IntRange3D>()

    fun apply(rebootStep: RebootStep) {
        if (range3D doesNotContain rebootStep.range3D) return
        if (rebootStep.action == RebootAction.TURN_ON && activatedCubesClusters.any { it contains rebootStep.range3D }) return

        val newCubeClusters = activatedCubesClusters.flatMap { it - rebootStep.range3D }.toMutableList()
        if (rebootStep.action == RebootAction.TURN_ON) newCubeClusters += rebootStep.range3D
        activatedCubesClusters = newCubeClusters
    }
}

data class IntRange3D(
    val xRange: IntRange,
    val yRange: IntRange,
    val zRange: IntRange
) {

    val size: Long by lazy { xRange.size * yRange.size * zRange.size }

    operator fun minus(other: IntRange3D): List<IntRange3D> {
        if (other == this || other contains this) return emptyList()

        // split this cluster into sub-clusters along the edges of the other cluster
        val xCoordsRanges = getSubRanges(other) { it.xRange }
        val yCoordsRanges = getSubRanges(other) { it.yRange }
        val zCoordsRanges = getSubRanges(other) { it.zRange }
        val resultingClusters = mutableListOf<IntRange3D>()
        for (xCoordRange in xCoordsRanges) {
            for (yCoordRange in yCoordsRanges) {
                for (zCoordRange in zCoordsRanges) {
                    resultingClusters += IntRange3D(xCoordRange, yCoordRange, zCoordRange)
                }
            }
        }

        // only keep the clusters which are not contained by the other cluster
        val subClusters = resultingClusters.filterNot { other contains it }
        return merge(subClusters)
    }

    private fun merge(clusters: List<IntRange3D>): List<IntRange3D> {
        var mergedClusters = clusters
        var previousMergedClusters: List<IntRange3D>
        do {
            previousMergedClusters = mergedClusters

            val newClusters = mutableListOf<IntRange3D>()
            for (cluster in clusters) {
                var clusterToRemove: IntRange3D? = null
                var clusterToAdd: IntRange3D = cluster
                for (newCluster in newClusters) {
                    val addResult = cluster + newCluster
                    if (addResult.size == 1) {
                        clusterToAdd = addResult[0]
                        clusterToRemove = newCluster
                        break
                    }
                }
                clusterToRemove?.let { newClusters -= clusterToRemove }
                newClusters += clusterToAdd
            }
            mergedClusters = newClusters
        } while (previousMergedClusters.size != mergedClusters.size)
        return mergedClusters
    }

    operator fun plus(other: IntRange3D): List<IntRange3D> {
        if (this.xRange continues other.xRange || other.xRange continues this.xRange) {
            if (this.yRange == other.yRange && this.zRange == other.zRange) {
                val xMin = min(this.xRange.first, other.xRange.first)
                val xMax = max(this.xRange.last, other.xRange.last)
                return listOf(IntRange3D(xMin..xMax, this.yRange, this.zRange))
            }
        } else if (this.yRange continues other.yRange || other.yRange continues this.yRange) {
            if (this.xRange == other.xRange && this.zRange == other.zRange) {
                val yMin = min(this.yRange.first, other.yRange.first)
                val yMax = max(this.yRange.last, other.yRange.last)
                return listOf(IntRange3D(this.xRange, yMin..yMax, this.zRange))
            }
        } else if (this.zRange continues other.zRange || other.zRange continues this.zRange) {
            if (this.xRange == other.xRange && this.yRange == other.yRange) {
                val zMin = min(this.zRange.first, other.zRange.first)
                val zMax = max(this.zRange.last, other.zRange.last)
                return listOf(IntRange3D(this.xRange, this.yRange, zMin..zMax))
            }
        }
        return listOf(this, other)
    }

    private fun getSubRanges(
        other: IntRange3D,
        getRange: (IntRange3D) -> IntRange
    ): List<IntRange> {
        val thisRange = getRange(this)
        val otherRange = getRange(other)

        val coordRanges = mutableListOf<IntRange>()
        var previousCoord = thisRange.first
        if ((otherRange.first in thisRange) && (otherRange.first - 1 in thisRange)) {
            coordRanges += previousCoord until otherRange.first
            previousCoord = otherRange.first
        }
        if ((otherRange.last in thisRange) && (otherRange.last + 1 in thisRange)) {
            coordRanges += previousCoord..otherRange.last
            previousCoord = otherRange.last + 1
        }
        coordRanges += previousCoord..thisRange.last
        return coordRanges
    }

    infix fun doesNotContain(other: IntRange3D): Boolean = !this.contains(other)

    infix fun contains(other: IntRange3D): Boolean {
        return this.xRange contains other.xRange &&
                this.yRange contains other.yRange &&
                this.zRange contains other.zRange
    }
}

data class RebootStep(val action: RebootAction, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
    val range3D = IntRange3D(xRange, yRange, zRange)
}

enum class RebootAction {
    TURN_ON, TURN_OFF
}


////////////////////////////////////////////////////////////////////////////////////////////
// EXTENSIONS
////////////////////////////////////////////////////////////////////////////////////////////

private val IntRange.size: Long
    get() = this.last.toLong() - this.first + 1

private infix fun IntRange.contains(other: IntRange): Boolean {
    return (this.first <= other.first) && (this.last >= other.last)
}

private infix fun IntRange.continues(other: IntRange): Boolean {
    return this.first == other.last + 1
}
