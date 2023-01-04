package aoc2020.day04

import utils.split
import java.util.function.Predicate

/**
 * My solution for day 4 of Advent of Code 2020.
 * The puzzle can be found at the <a href="https://adventofcode.com/2020/day/4">AoC page</a>.
 */
object Day04 {

    fun part1(input: List<String>): Int {
        val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        return readInputAsPassports(input).count { it.fieldNames.containsAll(requiredFields) }
    }

    fun part2(input: List<String>): Int {
        val fieldRules = listOf(
            createRangeFieldRule("byr", 1920..2002),
            createRangeFieldRule("iyr", 2010..2020),
            createRangeFieldRule("eyr", 2020..2030),
            createHeightFieldRule("hgt", 150..193, 59..76),
            createRegexFieldRule("hcl", "#[0-9a-f]{6}"),
            createRegexFieldRule("ecl", "amb|blu|brn|gry|grn|hzl|oth"),
            createRegexFieldRule("pid", "[0-9]{9}"),
        )
        return readInputAsPassports(input).count { passport -> fieldRules.all { rule -> rule.test(passport) } }
    }

}

private fun createRangeFieldRule(fieldName: String, range: IntRange): Predicate<Passport> {
    return Predicate {
        val value = it.getFieldValue(fieldName)?.toIntOrNull() ?: return@Predicate false
        value in range
    }
}

private fun createHeightFieldRule(fieldName: String, cmRange: IntRange, inRange: IntRange): Predicate<Passport> {
    return Predicate {
        val strValue = it.getFieldValue(fieldName) ?: return@Predicate false
        val (value, unit) = strValue.split(strValue.length - 2)
        val heightValue = value.toIntOrNull() ?: return@Predicate false
        when(unit) {
            "cm" -> heightValue in cmRange
            "in" -> heightValue in inRange
            else -> false
        }
    }
}

private fun createRegexFieldRule(fieldName: String, pattern: String): Predicate<Passport> {
    return Predicate {
        val value = it.getFieldValue(fieldName) ?: return@Predicate false
        pattern.toRegex().matches(value)
    }
}


private fun readInputAsPassports(input: List<String>): List<Passport> {
    return input.split { it.isEmpty() }
        .filter { it.isNotEmpty() }
        .map { Passport.from(it) }
}