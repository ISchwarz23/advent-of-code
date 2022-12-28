package aoc2022

object Day20 {

    fun part1(input: List<String>): Long {
        val file = input.map { it.toLong() }.withIndex().toMutableList()
        decrypt(file)
        return getGroveCoordinates(file)
    }

    fun part2(input: List<String>): Long {
        val file = input.map { it.toLong() }.map { it * 811_589_153 }.withIndex().toMutableList()
        repeat(10) {
            decrypt(file)
        }
        return getGroveCoordinates(file)
    }

}

private fun decrypt(file: MutableList<IndexedValue<Long>>) {
    file.indices.forEach { index ->
        val currentIndex = file.indexOfFirst { it.index == index }
        val number = file.removeAt(currentIndex)
        file.add((currentIndex + number.value).mod(file.size), number)
    }
}

private fun getGroveCoordinates(file: MutableList<IndexedValue<Long>>): Long {
    val zeroIndex = file.indexOfFirst { it.value == 0L }
    return listOf(1000, 2000, 3000).sumOf { file[(zeroIndex + it) % file.size].value }
}