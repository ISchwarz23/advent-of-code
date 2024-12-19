package aoc2023.day15

/**
 * My solution for day 15 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/15">AoC page</a>.
 */
object Day15 {

    fun part1(input: List<String>): Int {
        return input.sumOf { it.hash256() }
    }

    fun part2(input: List<String>): Int {
        val boxes = mutableMapOf<Int, LinkedHashMap<String, Int>>()

        input.map { it.split("[=-]".toRegex()) }
            .map { (name: String, focalLength) ->
                if (focalLength.isEmpty()) LensAction.Remove(name) else LensAction.Add(name, focalLength.toInt())
            }
            .forEach {
                when (it) {
                    is LensAction.Add -> boxes.compute(it.boxIndex) { _, slots ->
                        val adaptedSlots = slots ?: linkedMapOf()
                        adaptedSlots[it.name] = it.focalLength
                        adaptedSlots
                    }

                    is LensAction.Remove -> boxes[it.boxIndex]?.remove(it.name)
                }
            }

        return boxes.flatMap { (boxIndex, slots) ->
            slots.entries.mapIndexed { slotIndex, (_, focalLength) ->
                (boxIndex + 1) * (slotIndex + 1) * focalLength
            }
        }.sum()
    }

}

private sealed class LensAction(val name: String) {
    val boxIndex = name.hash256()

    class Add(name: String, val focalLength: Int) : LensAction(name)
    class Remove(name: String) : LensAction(name)
}

fun String.hash256(): Int {
    var hash = 0
    for (char in chars()) {
        hash += char
        hash *= 17
        hash %= 256
    }
    return hash
}