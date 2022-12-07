package aoc2022

object Day07 {

    fun part1(input: List<String>): Long {
        val rootDir = parseInput(input)
        return getDirectorySizes(rootDir).filter { it < 100000 }.sum()
    }

    fun part2(input: List<String>): Long {
        val rootDir = parseInput(input)
        val spaceToFreeUp = rootDir.size - 70000000 + 30000000
        return getDirectorySizes(rootDir).sorted().find { it > spaceToFreeUp }!!
    }
}

private fun parseInput(input: List<String>): Directory {
    val inputReader = InputHelper(input)
    inputReader.getNextLine()
    val rootDir = Directory("/")
    parseDir(rootDir, inputReader)
    return rootDir
}

private fun getDirectorySizes(dir: Directory): MutableList<Long> {
    val sizes = mutableListOf<Long>()

    for (file in dir.files) {
        if (file is Directory) {
            sizes += file.size
            sizes += getDirectorySizes(file)
        }
    }
    return sizes
}

private fun print(dir: Directory, indent: Int = 0) {
    repeat(indent) { print(" ") }
    println("- ${dir.name} (dir, size=${dir.size})")

    for (file in dir.files) {
        if (file is File) {
            repeat(indent) { print(" ") }
            println("  - ${file.name} (file, size=${file.size})")
        } else if (file is Directory) {
            print(file, indent + 2)
        }
    }
}

private class InputHelper(private val input: List<String>) {
    val lineNumber: Int
        get() = lineIndex + 1
    private var lineIndex = -1

    fun getNextLine(): String? {
        return if (moreLinesAvailable()) {
            input[++lineIndex]
        } else {
            null
        }
    }

    fun moreLinesAvailable(): Boolean {
        return input.size - 1 > lineIndex
    }

}

private fun parseDir(dir: Directory, input: InputHelper) {
    if (input.getNextLine() != "$ ls") throw RuntimeException("Missing ls command")

    var line = input.getNextLine()
    while (line != null && !line.startsWith("$ ")) {
        if (line.startsWith("dir ")) {
            val parts = line.split(" ")
            dir.files += Directory(parts[1])
        } else {
            val parts = line.split(" ")
            dir.files += File(parts[1], parts[0].toLong())
        }
        line = input.getNextLine()
    }

    while (line != null && line != "$ cd ..") {
        if (line.startsWith("$ cd ")) {
            val targetFolderName = line.split(" ")[2]
            val subDir = dir.getSubDir(targetFolderName) ?: throw RuntimeException("Unknown folder $targetFolderName")
            parseDir(subDir, input)
        }
        line = input.getNextLine()
    }
}

interface FileSystemItem {
    val name: String
    val size: Long
}

data class Directory(
    override val name: String,
    val files: MutableList<FileSystemItem> = mutableListOf()
) : FileSystemItem {

    override val size: Long
        get() = files.sumOf { it.size }

    fun getSubDir(name: String): Directory? {
        return files.filterIsInstance<Directory>().find { it.name == name }
    }
}

data class File(
    override val name: String,
    override val size: Long
) : FileSystemItem