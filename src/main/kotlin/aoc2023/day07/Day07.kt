package aoc2023.day07

/**
 * My solution for day 7 of Advent of Code 2023.
 * The puzzle can be found at the <a href="https://adventofcode.com/2023/day/7">AoC page</a>.
 */
object Day07 {

    fun part1(input: List<String>): Long {
        return input.asSequence()
            .map { line -> line.split(" ") }
            .map { (cards, bid) -> Hand(cards.toCharArray().map { Card.fromChar(it, Card.JACK) }) to bid.toInt() }
            .sortedBy { (hand, _) -> hand }
            .mapIndexed { index, (_, bid) -> (index + 1L) * bid }
            .sum()
    }

    fun part2(input: List<String>): Long {
        return input.asSequence()
            .map { line -> line.split(" ") }
            .map { (cards, bid) -> Hand(cards.toCharArray().map { Card.fromChar(it, Card.JOKER) }) to bid.toInt() }
            .sortedBy { (hand, _) -> hand }
            .mapIndexed { index, (_, bid) -> (index + 1L) * bid }
            .sum()
    }

}


private data class Hand(val cards: List<Card>): Comparable<Hand> {

    enum class Type(val value: Int) {
        FIVE_OF_A_KIND(700),
        FOUR_OF_A_KIND(600),
        FULL_HOUSE(500),
        THREE_OF_A_KIND(400),
        TWO_PAIR(300),
        ONE_PAIR(200),
        HIGH_CARD(100)
    }

    val type: Type by lazy { getHandType(cards) }

    private fun getHandType(hand: List<Card>): Type {
        val cardCountByCardOriginal = hand.groupBy { it }.mapValues { it.value.size }
        val cardCountByCard = transformJokersInBestPossibleCards(cardCountByCardOriginal)
        val cardCounts = cardCountByCard.values

        return if (cardCounts.contains(5)) {
            Type.FIVE_OF_A_KIND
        } else if (cardCounts.contains(4)) {
            Type.FOUR_OF_A_KIND
        } else if (cardCounts.contains(3)) {
            if (cardCounts.contains(2)) {
                Type.FULL_HOUSE
            } else {
                Type.THREE_OF_A_KIND
            }
        } else if (cardCounts.count { it == 2 } == 2) {
            return Type.TWO_PAIR
        } else if (cardCounts.contains(2)) {
            return Type.ONE_PAIR
        } else {
            Type.HIGH_CARD
        }
    }

    private fun transformJokersInBestPossibleCards(cardCountByCardOriginal: Map<Card, Int>): Map<Card, Int> {
        val cardCountByCardOptimized = cardCountByCardOriginal.toMutableMap()

        val numberOfJokers = cardCountByCardOptimized.remove(Card.JOKER) ?: 0
        val entryMostCommonCard = cardCountByCardOptimized.entries.maxByOrNull { it.value } ?: MapEntry(Card.ACE, 0)
        cardCountByCardOptimized[entryMostCommonCard.key] = entryMostCommonCard.value + numberOfJokers
        return cardCountByCardOptimized
    }

    override fun compareTo(other: Hand): Int {
        val typeComparisonResult = this.type.value - other.type.value
        if (typeComparisonResult != 0) {
            return typeComparisonResult
        }
        return (0 until 5).asSequence()
            .map { index -> this.cards[index].value - other.cards[index].value }
            .find { result -> result != 0 } ?: 0
    }

}

private enum class Card(private val charRepresentation: Char, val value: Int) {
    ACE('A', 14),
    KING('K', 13),
    QUEEN('Q', 12),
    JACK('J', 11),
    NUMBER_10('T', 10),
    NUMBER_9('9', 9),
    NUMBER_8('8', 8),
    NUMBER_7('7', 7),
    NUMBER_6('6', 6),
    NUMBER_5('5', 5),
    NUMBER_4('4', 4),
    NUMBER_3('3', 3),
    NUMBER_2('2', 2),
    JOKER('J', 1);

    companion object {

        fun fromChar(char: Char, jInterpretation: Card): Card {
            return if (char == 'J') {
                jInterpretation
            } else {
                values().find { it.charRepresentation == char }!!
            }
        }
    }

}

private data class MapEntry<K, V>(override val key: K, override val value: V): Map.Entry<K, V>
