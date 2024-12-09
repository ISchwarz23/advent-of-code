package aoc2024.day09


/**
 * My solution for day 9 of Advent of Code 2024.
 * The puzzle can be found at the <a href="https://adventofcode.com/2024/day/9">AoC page</a>.
 */
object Day09 {

    private const val EMPTY_BLOCK = -1L

    fun part1(input: List<Char>): Long {

        // parsing
        val blocks = input.map { it.digitToInt() }
            .flatMapIndexed { index, length -> if (index % 2 == 0) List(length) { 1L * index / 2 } else List(length) { EMPTY_BLOCK } }
            .toMutableList()

        // compacting
        val dataLength = blocks.count { it != EMPTY_BLOCK }
        for (i in 0 until dataLength) {
            while (blocks[i] == EMPTY_BLOCK) {
                blocks[i] = blocks.removeLast()
            }
        }

        // calc checksum
        return blocks.takeWhile { it != EMPTY_BLOCK }
            .mapIndexed { index, value -> index * value }
            .sum()
    }

    fun part2(input: List<Char>): Long {

        // parsing
        val blockSequences = input.map { it.digitToInt() }
            .mapIndexed { index, length ->
                if (index % 2 == 0) BlockSequence(length, index / 2L)
                else BlockSequence(length, EMPTY_BLOCK)
            }
            .toMutableList()

        // compacting
        for (i in 0 until blockSequences.size) {
            if (blockSequences[i].id == EMPTY_BLOCK) {
                val emptyBlocks = blockSequences[i]
                val dataBlocksToMove =
                    blockSequences.findLast { it.id != EMPTY_BLOCK && it.length <= emptyBlocks.length }
                val indexOfDataBlocksToMove = blockSequences.indexOf(dataBlocksToMove)
                if (dataBlocksToMove != null && indexOfDataBlocksToMove > i) {
                    // shrink empty space
                    blockSequences.removeAt(i)
                    blockSequences.add(i, BlockSequence(emptyBlocks.length - dataBlocksToMove.length, EMPTY_BLOCK))
                    // replace data to move with empty space
                    blockSequences.removeAt(indexOfDataBlocksToMove)
                    blockSequences.add(indexOfDataBlocksToMove, BlockSequence(dataBlocksToMove.length, EMPTY_BLOCK))
                    // move data to new position
                    blockSequences.add(i, dataBlocksToMove)
                }
            }
        }

        // calc checksum
        return blockSequences.flatMap { blockSequence ->
            if (blockSequence.id == EMPTY_BLOCK) List(blockSequence.length) { 0L }
            else List(blockSequence.length) { blockSequence.id }
        }
            .mapIndexed { index, id -> index * id }
            .sum()
    }

}

private data class BlockSequence(val length: Int, val id: Long)
