package aoc2021.day16

import utils.split

object Day16 {

    fun part1(hexInput: String): Int {
        val binaryInput = convertHexToBin(hexInput)
        val pack = parsePackage(binaryInput).result
        return pack.getVersionSum()
    }

    fun part2(input: String): Long {
        val binaryInput = convertHexToBin(input)
        val pack = parsePackage(binaryInput).result
        return pack.getValue()
    }
}

private fun parsePackage(binaryInput: String): ParseResult<Package> {

    val (versionBin, rest1) = binaryInput.split(3)
    val version = versionBin.toInt(2)

    val (typeBin, rest2) = rest1.split(3)
    val type = typeBin.toInt(2)

    if (type == 4) {
        val literal = parseLiteralValue(rest2)
        return ParseResult(Package.Literal(version, type, literal.result), literal.numberOfParsedBits + 6)
    } else {
        val (lengthTypeBin, rest3) = rest2.split(1)
        return when (lengthTypeBin) {
            "0" -> {
                val (lengthBin, rest4) = rest3.split(15)
                val length = lengthBin.toInt(2)
                var restPayload = rest4.substring(0, length)

                val subPackages = mutableListOf<Package>()
                while (restPayload.contains("1")) {
                    val (parsedPackets, numberOfParsedBits) = parsePackage(restPayload)
                    subPackages += parsedPackets
                    restPayload = restPayload.substring(numberOfParsedBits)
                }
                ParseResult(Package.Operator(version, type, subPackages), length + 6 + 1 + 15)
            }

            "1" -> {
                val (numOfPacketsBin, rest4) = rest3.split(11)
                val numberOfPackages = numOfPacketsBin.toInt(2)
                var restPayload = rest4
                var length = 0

                val subPackages = mutableListOf<Package>()
                repeat(numberOfPackages) {
                    val (parsedPackets, numberOfParsedBits) = parsePackage(restPayload)
                    subPackages += parsedPackets
                    length += numberOfParsedBits
                    restPayload = restPayload.substring(numberOfParsedBits)
                }
                ParseResult(Package.Operator(version, type, subPackages), length + 6 + 1 + 11)
            }

            else -> throw NumberFormatException()
        }
    }
}

private fun parseLiteralValue(inputBits: String): ParseResult<Long> {

    var parsedBits = 0
    var literalValueBin = ""
    var restInputBits = inputBits
    do {
        val (currentBit, restBits) = restInputBits.split(5)
        restInputBits = restBits

        val (continueBit, valueBits) = currentBit.split(1)
        literalValueBin += valueBits
        parsedBits += 5
    } while (continueBit == "1")

    return ParseResult(literalValueBin.toLong(2), parsedBits)
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