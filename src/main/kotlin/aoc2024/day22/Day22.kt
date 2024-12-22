package aoc2024.day22

/**
 * My solution for day 22 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/22">AoC page</a>.
 */
object Day22 {

    fun part1(input: List<Int>): Long {
        return input.map { it.toLong() }.sumOf { initialNumber ->
            var nextRandomNumber = initialNumber
            repeat(2000) { nextRandomNumber = nextRandomNumber.nextPseudoRandomNumber }
            return@sumOf nextRandomNumber
        }
    }

    fun part2(input: List<Int>): Int {
        val stockCharts = input.map { toStockChart(it.toLong()) }
        return stockCharts.asSequence()
            .flatMap { it.changeSequences.asSequence() }
            .distinct()
            .maxOf { changeSequence ->
                stockCharts.sumOf { stock -> stock.getSellPrice(changeSequence) ?: 0 }
            }
    }

}

private fun toStockChart(startValue: Long): StockChart {
    return StockChart(generateSequence(startValue) { it.nextPseudoRandomNumber }
        .take(2000)
        .map { "$it".last().digitToInt() }
        .windowed(2)
        .map { (firstPrice, secondPrice) -> StockPrice(secondPrice, secondPrice - firstPrice) }
        .toList())
}

private data class StockPrice(val price: Int, val priceChange: Int)

private data class StockChart(val prices: List<StockPrice>, private val monkeySellSequenceLength: Int = 4) {

    private val changeSequencesToSellPrice = prices.windowed(monkeySellSequenceLength)
        .groupBy({ subChart -> subChart.map { it.priceChange } }) { subChart -> subChart.last().price }
        .mapValues { (_, prices) ->
            prices.first()
        }

    val changeSequences: Set<List<Int>> = changeSequencesToSellPrice.keys

    fun getSellPrice(changeSequence: List<Int>): Int? {
        if (changeSequence.size != monkeySellSequenceLength) {
            throw RuntimeException("Invalid change sequence length: ${changeSequence.size}")
        }
        return changeSequencesToSellPrice[changeSequence]
    }

}

private val Long.nextPseudoRandomNumber: Long
    get() {
        var nextSecretNumber = ((this * 64) xor this) % 16777216
        nextSecretNumber = ((nextSecretNumber / 32) xor nextSecretNumber) % 16777216
        return ((nextSecretNumber * 2048) xor nextSecretNumber) % 16777216
    }
