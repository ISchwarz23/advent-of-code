package aoc2022.day07


sealed interface FileSystemItem {
    val name: String
    val size: Long
}

data class Directory(
    override val name: String,
    val files: MutableList<FileSystemItem> = mutableListOf()
) : FileSystemItem {

    override val size: Long by lazy { files.sumOf { it.size } }

    fun getSubDirByName(name: String): Directory? {
        return files.filterIsInstance<Directory>().find { it.name == name }
    }
}

data class File(
    override val name: String,
    override val size: Long
) : FileSystemItem