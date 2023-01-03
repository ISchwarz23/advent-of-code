package aoc2021.day10

class SyntaxChecker {

    private val chunkOpenChars = listOf('(', '[', '{', '<')
    private val chunkCloseChars = listOf(')', ']', '}', '>')
    private val corruptedChunkCloseCharScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )
    private val missingChunkCloseCharScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    fun getCorruptedChunkScore(line: String): Int {
        val stack = ArrayDeque<Char>()
        line.toCharArray().forEach {
            when (it) {
                in chunkOpenChars ->
                    stack.addFirst(getChunkClosingCharFor(it))
                in chunkCloseChars -> {
                    if (stack.removeFirst() != it) {
                        return corruptedChunkCloseCharScores[it] ?: 0
                    }
                }
            }
        }
        return 0
    }

    fun getMissingCloseChunkScore(line: String): Long {
        val stack = ArrayDeque<Char>()
        line.toCharArray().forEach {
            when (it) {
                in chunkOpenChars ->
                    stack.addFirst(getChunkClosingCharFor(it))
                in chunkCloseChars -> {
                    if (stack.removeFirst() != it) return 0
                }
            }
        }

        var score = 0L
        while (stack.isNotEmpty()) {
            score *= 5
            score += missingChunkCloseCharScores[stack.removeFirst()] ?: 0
        }
        return score
    }

    private fun getChunkClosingCharFor(openChar: Char): Char {
        val index = chunkOpenChars.indexOf(openChar)
        return chunkCloseChars[index]
    }

}
