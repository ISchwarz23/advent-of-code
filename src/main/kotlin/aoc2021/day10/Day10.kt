package aoc2021.day10

object Day10 {

    fun part1(input: List<String>): Int {
        val syntaxChecker = SyntaxChecker()
        return input.sumOf { syntaxChecker.getCorruptedChunkScore(it) }
    }

    fun part2(input: List<String>): Long {
        val syntaxChecker = SyntaxChecker()
        val scores = input.map { syntaxChecker.getMissingCloseChunkScore(it) }.filter { it != 0L }.sorted()
        return scores[scores.size / 2]
    }

}