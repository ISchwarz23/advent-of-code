package aoc2022.day07

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
            val subDir = dir.getSubDirByName(targetFolderName) ?: throw RuntimeException("Unknown folder $targetFolderName")
            parseDir(subDir, input)
        }
        line = input.getNextLine()
    }
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

private class InputHelper(private val input: List<String>) {
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

sealed interface FileSystemItem {
    val name: String
    val size: Long
}

data class Directory(
    override val name: String,
    val files: MutableList<FileSystemItem> = mutableListOf()
) : FileSystemItem {

    override val size: Long
        get() = files.sumOf { it.size }

    fun getSubDirByName(name: String): Directory? {
        return files.filterIsInstance<Directory>().find { it.name == name }
    }
}

data class File(
    override val name: String,
    override val size: Long
) : FileSystemItem