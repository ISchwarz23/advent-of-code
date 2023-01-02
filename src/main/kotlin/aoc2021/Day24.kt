package aoc2021

object Day24 {

    private val highestSerialNumber = SerialNumber(98999999999999)
    private val smallestSerialNumber = SerialNumber(11111111111111)

    fun part1(instructions: List<Instruction>): Long {

        // TODO: split blocks add read instructions and cache results
        val instructionBlocks = mutableListOf<Pair<Instruction, InstructionBlock>>()
        var lastInputInstruction: Instruction? = null
        var blockInstructions: MutableList<Instruction> = mutableListOf()
        for (instruction in instructions) {
            if (instruction.operation == Operation.INPUT) {
                if (lastInputInstruction != null) {
                    instructionBlocks += Pair(lastInputInstruction, InstructionBlock(blockInstructions))
                }
                lastInputInstruction = instruction
                blockInstructions = mutableListOf()
            } else {
                blockInstructions += instruction
            }
        }

        val cache = mutableMapOf<Pair<Int, Vars>, Vars>()

        val serialNumberRange = highestSerialNumber..smallestSerialNumber
        for (serialNumber in serialNumberRange) {
            // println("Testing $serialNumber (Cache Size: ${cache.size})")
            val serialNumberReader = serialNumber.getDigitIterator()

            var vars = Vars.ALL_ZERO
            for(index in instructionBlocks.indices) {
                val pair = instructionBlocks[index]

                if(vars.z > 10000) break

                vars = pair.first.execute(vars, serialNumberReader)
                val cachedResult = cache[Pair(index, vars)]
                vars = if (cachedResult == null) {
                    val result = pair.second.execute(vars, serialNumberReader)
                    cache[Pair(index, vars)] = result
                    result
                } else {
                    cachedResult
                }
            }

            if (vars.z == 0) return serialNumber.toLong()
        }
        return 0L
    }

    fun part2(instructions: List<Instruction>): Long {
        return 0L
    }

}

data class SerialNumber(private val value: Long) {

    fun getDigitIterator(): SerialNumberDigitIterator =
        SerialNumberDigitIterator(value.toString().toCharArray().map { it.digitToInt() })

    fun toLong(): Long {
        return value
    }

    override fun toString(): String {
        return "" + value
    }

    operator fun rangeTo(end: SerialNumber): SerialNumberRange {
        return SerialNumberRange(this, end)
    }

    operator fun inc(): SerialNumber {
        var value = this.toLong()
        do {
            ++value
        } while ("$value".contains('0'))
        return SerialNumber(value)
    }

    operator fun dec(): SerialNumber {
        var value = this.toLong()
        do {
            --value
        } while ("$value".contains('0'))
        return SerialNumber(value)
    }
}

data class SerialNumberRange(val first: SerialNumber, val last: SerialNumber) : Iterable<SerialNumber> {

    override fun iterator(): Iterator<SerialNumber> {
        return if (first.toLong() < last.toLong()) {
            UpwardIterator(first, last)
        } else {
            DownwardIterator(first, last)
        }
    }

    private class UpwardIterator(from: SerialNumber, private val to: SerialNumber) :
        Iterator<SerialNumber> {

        private var current = from

        override fun hasNext(): Boolean = current.toLong() <= to.toLong()

        override fun next(): SerialNumber {
            val next = current
            current = current++
            return next
        }
    }

    private class DownwardIterator(from: SerialNumber, private val to: SerialNumber) :
        Iterator<SerialNumber> {

        private var current = from

        override fun hasNext(): Boolean = current.toLong() >= to.toLong()

        override fun next(): SerialNumber {
            val next = current
            current = current.dec()
            return next
        }
    }

}


data class SerialNumberDigitIterator(val digits: List<Int>) {

    private var pointer = -1

    fun readNextDigit(): Int {
        if (hasMoreDigits().not()) throw RuntimeException("No more digits")
        return digits[++pointer]
    }

    fun hasMoreDigits(): Boolean {
        return pointer + 1 < digits.size
    }
}

enum class Operation {
    INPUT, ADD, MULTIPLY, DIVIDE, MODULO, EQUALS
}

data class Instruction(val operation: Operation, val firstVar: String, val secondVar: String?) {

    fun execute(vars: Vars, reader: SerialNumberDigitIterator): Vars {

        fun parse(variable: String): Int {
            return when (variable) {
                "w" -> vars.w
                "x" -> vars.x
                "y" -> vars.y
                "z" -> vars.z
                else -> variable.toInt()
            }
        }

        val firstVarParsed = parse(firstVar)
        val secondVarParsed = if (secondVar == null) 0 else parse(secondVar)
        val result = when (operation) {
            Operation.INPUT -> reader.readNextDigit()
            Operation.ADD -> firstVarParsed + secondVarParsed
            Operation.MULTIPLY -> firstVarParsed * secondVarParsed
            Operation.DIVIDE -> firstVarParsed / secondVarParsed
            Operation.MODULO -> firstVarParsed % secondVarParsed
            Operation.EQUALS -> if (firstVarParsed == secondVarParsed) 1 else 0
        }

        return when (firstVar) {
            "w" -> vars.copy(w = result)
            "x" -> vars.copy(x = result)
            "y" -> vars.copy(y = result)
            "z" -> vars.copy(z = result)
            else -> throw IllegalArgumentException("Unknown var '$firstVar'!")
        }
    }

}

data class InstructionBlock(val instructions: List<Instruction>) {

    fun execute(vars: Vars, reader: SerialNumberDigitIterator): Vars {
        var result = vars
        for (instruction in instructions) result = instruction.execute(result, reader)
        return result
    }

}

data class Vars(val w: Int, val x: Int, val y: Int, val z: Int) {

    companion object {
        val ALL_ZERO: Vars = Vars(0, 0, 0, 0)
    }

}

