package aoc2022

import kotlin.math.min

object Day13 {

    fun part1(input: List<String>): Int {
        return input.chunked(3)
            .map { Pair(parsePacketList(it[0]), parsePacketList(it[1])) }
            .mapIndexed { index, pair -> if (compare(pair.first, pair.second) <= 0) index + 1 else 0 }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val firstSeparator = Packet.List(listOf(Packet.List(listOf(Packet.Item(2)))))
        val secondSeparator = Packet.List(listOf(Packet.List(listOf(Packet.Item(6)))))

        val packets = input.filter { it.isNotBlank() }.map { parsePacketList(it) }.toMutableList()
        packets += firstSeparator
        packets += secondSeparator
        packets.sortWith { first: Packet, second: Packet -> compare(first, second) }
        return (packets.indexOf(firstSeparator) + 1) * (packets.indexOf(secondSeparator) + 1)
    }
}

private fun compare(left: Packet, right: Packet): Int {
    // if both packets are items, compare values
    if (left is Packet.Item && right is Packet.Item) return left.value - right.value

    // else transform both packets to list (if not already), compare items
    val leftList = left.toPacketList()
    val rightList = right.toPacketList()
    for (i in 0 until min(leftList.size, rightList.size)) {
        val result = compare(leftList.content[i], rightList.content[i])
        if (result != 0) return result
    }
    return leftList.size - rightList.size
}

private fun parsePacketList(input: String): Packet.List {
    fun parsePacketListRec(input: String, startIndex: Int): Pair<Packet.List, Int> {
        val subPackets = mutableListOf<Packet>()
        var currentIndex = startIndex
        while (currentIndex < input.length && input[currentIndex] != ']') {
            if (input[currentIndex] == ',') currentIndex++
            if (input[currentIndex] == '[') {
                val result = parsePacketListRec(input, currentIndex + 1)
                subPackets += result.first
                currentIndex += result.second + 1
            } else {
                var endIndex1 = input.indexOf(',', currentIndex)
                if (endIndex1 < 0) endIndex1 = Int.MAX_VALUE
                var endIndex2 = input.indexOf(']', currentIndex)
                if (endIndex2 < 0) endIndex2 = Int.MAX_VALUE
                val endIndex = min(endIndex1, endIndex2)
                subPackets += Packet.Item(input.substring(currentIndex, endIndex).toInt())
                currentIndex = endIndex
            }
        }
        return Pair(Packet.List(subPackets), currentIndex - startIndex + 1)
    }

    return parsePacketListRec(input, 1).first
}

sealed class Packet {

    abstract fun toPacketList(): Packet.List

    data class List(
        val content: kotlin.collections.List<Packet>
    ) : Packet() {

        val size = content.size

        override fun toPacketList(): List = this

        override fun toString(): String {
            return content.joinToString(separator = ",", prefix = "[", postfix = "]") { it.toString() }
        }
    }

    data class Item(
        val value: Int
    ) : Packet() {

        override fun toPacketList(): List = List(listOf(this))

        override fun toString(): String {
            return "$value"
        }
    }
}

