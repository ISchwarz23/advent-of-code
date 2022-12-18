package utils

import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("input", name).readLines()

/**
 * Reads lines from the given input txt file as in values.
 */
fun readInputAsInts(name: String) = readInput(name).map { it.toInt() }

/**
 * Reads lines from the given input file as Vector2D
 */
fun readInputAsVector2D(name: String, delimiter: String = ",") =
    readInput(name).map { it.split(delimiter) }.map { Vector2(it[0].toInt(), it[1].toInt()) }


/**
 * Reads lines from the given input file as Vector3D
 */
fun readInputAsVector3D(name: String, delimiter: String = ",") =
    readInput(name).map { it.split(delimiter) }.map { Vector3(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

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

fun readInputAs2dIntArray(name: String, delimiter: String = ""): List<List<Int>> {
    return readInput(name).map { it.toCharArray().map { c -> c.digitToInt() } }
}