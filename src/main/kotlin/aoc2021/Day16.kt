package aoc2021

import utils.split

object Day16 {

    fun part1(hexInput: String): Int {
        val binaryInput = convertHexToBin(hexInput)
        val pack = parsePackage(binaryInput).result
        return getVersionSum(pack)
    }

    fun part2(input: String): Long {
        val binaryInput = convertHexToBin(input)
        val pack = parsePackage(binaryInput).result
        return pack.getValue()
    }

    private fun getVersionSum(pack: Package): Int {
        return when (pack) {
            is LiteralPackage -> pack.version
            is OperatorPackage -> pack.version + pack.subPackages.sumOf { getVersionSum(it) }
        }
    }

    private fun parsePackage(binaryInput: String): ParseResult<Package> {

        var readResult = binaryInput.split(3)
        val version = readResult.first.toInt(2)

        readResult = readResult.second.split(3)
        val type = readResult.first.toInt(2)

        if (type == 4) {
            val literal = parseLiteralValue(readResult.second)
            return ParseResult(LiteralPackage(version, type, literal.result), literal.numberOfParsedBits + 6)
        } else {
            readResult = readResult.second.split(1)
            return when (readResult.first) {
                "0" -> {
                    readResult = readResult.second.split(15)
                    val length = readResult.first.toInt(2)
                    var restPayload = readResult.second.substring(0, length)

                    val subPackages = mutableListOf<Package>()
                    while (restPayload.contains("1")) {
                        val result = parsePackage(restPayload)
                        subPackages += result.result
                        restPayload = restPayload.substring(result.numberOfParsedBits)
                    }
                    ParseResult(OperatorPackage(version, type, subPackages), length + 6 + 1 + 15)
                }
                "1" -> {
                    readResult = readResult.second.split(11)
                    val numberOfPackages = readResult.first.toInt(2)
                    var restInput = readResult.second
                    var length = 0

                    val subPackages = mutableListOf<Package>()
                    repeat(numberOfPackages) {
                        val result = parsePackage(restInput)
                        subPackages += result.result
                        length += result.numberOfParsedBits
                        restInput = restInput.substring(result.numberOfParsedBits)
                    }
                    ParseResult(OperatorPackage(version, type, subPackages), length + 6 + 1 + 11)
                }
                else -> throw NumberFormatException()
            }
        }
    }

    private fun parseLiteralValue(binaryInput: String): ParseResult<Long> {

        var length = 0
        var binaryValue = ""
        var restInput = binaryInput
        do {
            val splitResult = restInput.split(5)
            val currentBits = splitResult.first
            restInput = splitResult.second
            binaryValue += currentBits.substring(1, 5)
            length += 5
        } while (currentBits.first() == '1')
        return ParseResult(binaryValue.toLong(2), length)
    }
}


private data class ParseResult<T>(val result: T, val numberOfParsedBits: Int)

private sealed interface Package {
    val version: Int
    val type: Int
    fun getValue(): Long
}

private data class LiteralPackage(
    override val version: Int,
    override val type: Int,
    val literal: Long
) : Package {

    override fun getValue(): Long = literal
    override fun toString(): String = "$literal"
}

private data class OperatorPackage(
    override val version: Int,
    override val type: Int,
    val subPackages: List<Package>
) : Package {

    override fun getValue(): Long {
        return when (type) {
            0 -> subPackages.sumOf { it.getValue() }
            1 -> subPackages.fold(1) { acc, pack -> acc * pack.getValue() }
            2 -> subPackages.minOf { it.getValue() }
            3 -> subPackages.maxOf { it.getValue() }
            5 -> if (subPackages[0].getValue() > subPackages[1].getValue()) 1 else 0
            6 -> if (subPackages[0].getValue() < subPackages[1].getValue()) 1 else 0
            7 -> if (subPackages[0].getValue() == subPackages[1].getValue()) 1 else 0
            else -> throw RuntimeException("Unknown operator type")
        }
    }

    override fun toString(): String {
        return when (type) {
            0 -> subPackages.joinToString(" + ", "(", ")") { "" + it }
            1 -> subPackages.joinToString(" * ", "(", ")") { "" + it }
            2 -> subPackages.joinToString(", ", "min(", ")") { "" + it }
            3 -> subPackages.joinToString(", ", "max(", ")") { "" + it }
            5 -> "(${subPackages[0]} > ${subPackages[1]})"
            6 -> "(${subPackages[0]} < ${subPackages[1]})"
            7 -> "(${subPackages[0]} == ${subPackages[1]})"
            else -> throw RuntimeException("Unknown operator type")
        }
    }

}

private fun convertHexToBin(hexString: String): String {
    val mapping: Map<Char, String> = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111"
    )
    return hexString.map { mapping[it] }.joinToString("")
}