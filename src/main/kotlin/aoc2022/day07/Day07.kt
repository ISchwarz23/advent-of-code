package aoc2022.day07

object Day07 {

    fun part1(input: List<String>): Long {
        val rootDir = parseDir(Directory("/"), InputReader(input))
        return getDirectorySizes(rootDir).filter { it < 100_000 }.sum()
    }

    fun part2(input: List<String>): Long {
        val rootDir = parseDir(Directory("/"), InputReader(input))
        val spaceToFreeUp = rootDir.size - 70_000_000 + 30_000_000
        return getDirectorySizes(rootDir).sorted().find { it > spaceToFreeUp } ?: error("No dir to delete found")
    }
}

private fun parseDir(dir: Directory, input: InputReader): Directory {
    if (input.getNextLine() != "$ ls") error("Missing ls command")

    var line = input.getNextLine() ?: return dir
    while (!line.startsWith("$ ")) {
        if (line.startsWith("dir ")) {
            val parts = line.split(" ")
            dir.files += Directory(parts[1])
        } else {
            val parts = line.split(" ")
            dir.files += File(parts[1], parts[0].toLong())
        }
        line = input.getNextLine() ?: return dir
    }

    while (line != "$ cd ..") {
        if (line.startsWith("$ cd ")) {
            val targetFolderName = line.split(" ")[2]
            val subDir = dir.getSubDirByName(targetFolderName) ?: error("Unknown folder $targetFolderName")
            parseDir(subDir, input)
        }
        line = input.getNextLine() ?: return dir
    }

    return dir
}

private fun getDirectorySizes(dir: Directory): List<Long> {
    val sizes = mutableListOf<Long>()

    for (file in dir.files) {
        if (file is Directory) {
            sizes += file.size
            sizes += getDirectorySizes(file)
        }
    }
    return sizes
}

