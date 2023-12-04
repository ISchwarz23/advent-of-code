package aoc2023.day04

import utils.pow

/**
 * My solution for day 4 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/4">AoC page</a>.
 */
object Day04 {

    fun part1(input: List<String>): Int {
        return input.map { line -> parseCard(line) }
            .filter { card -> card.numberOfMatchingValues > 0 }
            .sumOf { card -> 2 pow (card.numberOfMatchingValues - 1) }
    }

    fun part2(input: List<String>): Int {
        val cardIdRange = 1..input.size
        val countByCardId = cardIdRange.associateWith { 1 }.toMutableMap()

        input.map { line -> parseCard(line) }
            .filter { card -> card.numberOfMatchingValues > 0 }
            .sortedBy { card -> card.id } // could be removed, as input is already sorted
            .forEach { currentCard ->
                val cardCopyRange = (currentCard.id + 1)..(currentCard.id + currentCard.numberOfMatchingValues)
                cardCopyRange.forEach { cardToCopy ->
                    countByCardId[cardToCopy] = countByCardId[cardToCopy]!! + countByCardId[currentCard.id]!!
                }
            }

        return countByCardId.values.sum()
    }

}


private val ONE_OR_MORE_SPACES_REGEX = "\\s+".toRegex()
private val NUMBER_REGEX = "\\d+".toRegex()

private fun parseCard(line: String): Card {
    val (cardId, values) = line.split(": ")
    val (winningValues, myValues) = values.split(" | ")
    return Card(
        NUMBER_REGEX.find(cardId)!!.value.toInt(),
        winningValues.trim().split(ONE_OR_MORE_SPACES_REGEX).map { it.toInt() },
        myValues.trim().split(ONE_OR_MORE_SPACES_REGEX).map { it.toInt() }
    )
}

private data class Card(
    val id: Int,
    val winningValues: List<Int>,
    val myValues: List<Int>,
) {

    val matchingNumbers: List<Int> = myValues.filter { myCard -> winningValues.contains(myCard) }
    val numberOfMatchingValues: Int = matchingNumbers.size

}