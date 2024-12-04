package utils

import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> = File("input", name).readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readInputAsChars(name: String): List<List<Char>> = File("input", name).readLines().map { it.toCharArray().map { it } }

/**
 * Reads lines from the given input txt file as in values.
 */
fun readInputAsInts(name: String) = readInput(name).map { it.toInt() }

/**
 * Reads lines from the given input file as Vector2D
 */
fun readInputAsVector2(name: String, delimiter: String = ",") =
    readInput(name).map { it.split(delimiter) }.map { Vector2(it[0].toInt(), it[1].toInt()) }


/**
 * Reads lines from the given input file as Vector3D
 */
fun readInputAsVector3(name: String, delimiter: String = ",") =
    readInput(name).map { it.split(delimiter) }.map { Vector3(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

/**
 * Reads chars from given input file and passes it with its x and y indices to the given transform function.
 */
fun <T> readInput2dIndexed(name: String, transform: (x: Int, y: Int, data: Char) -> T?): List<T> {
    return readInput(name).flatMapIndexed { y, row -> row.mapIndexed { x, data -> transform(x, y, data) } }
        .filterNotNull()
}

/**
 * Reads one line as String.
 */
fun readOneLineInputAsString(name: String): String {
    return readInput(name)[0]
}

/**
 * Reads one line and map to Ints.
 */
fun readOneLineInputAsInts(name: String, delimiter: String = ","): List<Int> {
    return readInput(name)[0].split(delimiter).map { it.toInt() }
}

/**
 * Reads input as 2d-Int-Array.
 */
fun readInputAs2dIntArray(name: String, delimiter: String = ""): List<List<Int>> {
    return readInput(name).map { it.split(delimiter).filter { s -> s.isNotBlank() }.map { s -> s.toInt() } }
}