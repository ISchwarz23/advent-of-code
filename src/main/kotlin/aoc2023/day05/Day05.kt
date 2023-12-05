package aoc2023.day05

import utils.contains
import utils.overlaps
import utils.split

/**
 * My solution for day 5 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/5">AoC page</a>.
 */
object Day05 {

    fun part1(input: List<String>): Long {
        val seeds = input[0].substring(7).split(" ").map { it.toLong() }
        val mappers = input.split { it.isEmpty() }.map { parseMapper(it) }

        return seeds.asSequence()
            .map { mappers[0].applyMappingTo(it) }
            .map { mappers[1].applyMappingTo(it) }
            .map { mappers[2].applyMappingTo(it) }
            .map { mappers[3].applyMappingTo(it) }
            .map { mappers[4].applyMappingTo(it) }
            .map { mappers[5].applyMappingTo(it) }
            .map { mappers[6].applyMappingTo(it) }
            .minOf { it }
    }

    fun part2(input: List<String>): Long {

        val seedRanges = input[0].substring(7)
            .split(" ")
            .map { it.toLong() }
            .chunked(2)
            .map { it[0] until it[0] + it[1] }
        val mappers = input.split { it.isEmpty() }.map { parseMapper(it) }

        return seedRanges.asSequence()
            .flatMap { mappers[0].applyMappingTo(it) }
            .flatMap { mappers[1].applyMappingTo(it) }
            .flatMap { mappers[2].applyMappingTo(it) }
            .flatMap { mappers[3].applyMappingTo(it) }
            .flatMap { mappers[4].applyMappingTo(it) }
            .flatMap { mappers[5].applyMappingTo(it) }
            .flatMap { mappers[6].applyMappingTo(it) }
            .minOf { it.first }
    }

}

private fun parseMapper(mappingLines: List<String>): Mapper {
    val mapperName = mappingLines[0]
    val mappingInstructions = mappingLines.subList(1, mappingLines.size)
        .map { parseMappingInstruction(it) }
    return Mapper(mapperName, mappingInstructions)
}

private fun parseMappingInstruction(mappingInstructionStr: String): RangeShiftMapping {
    val mappingInstructionParams = mappingInstructionStr.split(" ").map { it.toLong() }
    return RangeShiftMapping(
        mappingInstructionParams[0],
        mappingInstructionParams[1],
        mappingInstructionParams[2]
    )
}


class Mapper(
    val name: String,
    private val mappings: List<Mapping>
) {

    fun applyMappingTo(sourceValue: Long): Long {
        val mapping = mappings.find { sourceValue in it.sourceRange } ?: NoOpMapping
        return mapping.applyTo(sourceValue)
    }

    fun applyMappingTo(sourceValueRange: LongRange): List<LongRange> {
        // find the ranges for which a mapping is available
        val subRanges = mappings.filter { it.sourceRange overlaps sourceValueRange }
            .map { it.sourceRange }
            .sortedBy { it.first }
            .toMutableList()

        if (subRanges.isEmpty()) {
            return listOf(sourceValueRange)
        }
        if (subRanges.size == 1) {
            return listOf(findAndApplyMapper(sourceValueRange))
        }

        // adapt left bound of first range or add range that covers missing values from the start of the source values
        if (subRanges.first().first <= sourceValueRange.first) {
            val previousRange = subRanges.removeFirst()
            subRanges.add(0, sourceValueRange.first..previousRange.last)
        } else {
            subRanges.add(0, sourceValueRange.first until subRanges.first().first)
        }

        // adapt right bound of last range or add range that covers missing values from the end of the source values
        if (subRanges.last().last >= sourceValueRange.last) {
            val previousRange = subRanges.removeLast()
            subRanges.add(previousRange.first..sourceValueRange.last)
        } else {
            subRanges.add((subRanges.last().last + 1)..sourceValueRange.last)
        }

        // create ranges for the gaps between the ranges with available mapping and apply mapping to every range
        return subRanges.windowed(2)
            .flatMap { (firstRange, secondRange) ->
                listOf(
                    firstRange,
                    firstRange.last + 1 until secondRange.first,
                    secondRange
                )
            }
            .filter { it.first <= it.last }
            .map { findAndApplyMapper(it) }
    }

    private fun findAndApplyMapper(sourceValues: LongRange): LongRange {
        val mapper = mappings.find { it.sourceRange contains sourceValues } ?: NoOpMapping
        return mapper.applyTo(sourceValues)
    }

}

interface Mapping {

    val sourceRange: LongRange
    val destinationRange: LongRange

    fun applyTo(sourceValue: Long): Long

    fun applyTo(sourceValues: LongRange): LongRange

}

object NoOpMapping : Mapping {

    override val sourceRange: LongRange = Long.MIN_VALUE..Long.MAX_VALUE
    override val destinationRange: LongRange = Long.MIN_VALUE..Long.MAX_VALUE

    override fun applyTo(sourceValue: Long): Long {
        return sourceValue
    }

    override fun applyTo(sourceValues: LongRange): LongRange {
        return sourceValues
    }

}

class RangeShiftMapping(
    destinationRangeStart: Long,
    sourceRangeStart: Long,
    rangeLength: Long
) : Mapping {

    override val sourceRange: LongRange = sourceRangeStart until sourceRangeStart + rangeLength
    override val destinationRange: LongRange = destinationRangeStart until destinationRangeStart + rangeLength
    private val shifting = destinationRangeStart - sourceRangeStart

    private fun canBeAppliedTo(sourceValue: Long): Boolean {
        return sourceValue in sourceRange
    }

    private fun canBeAppliedTo(sourceValues: LongRange): Boolean {
        return sourceRange contains sourceValues
    }

    override fun applyTo(sourceValue: Long): Long {
        if (!canBeAppliedTo(sourceValue)) throw RuntimeException("Mapping can't be applied to $sourceValue")
        return sourceValue + shifting
    }

    override fun applyTo(sourceValues: LongRange): LongRange {
        if (!canBeAppliedTo(sourceValues)) throw RuntimeException("Mapping can't be applied to $sourceValues")
        return (sourceValues.first + shifting)..(sourceValues.last + shifting)
    }

}
