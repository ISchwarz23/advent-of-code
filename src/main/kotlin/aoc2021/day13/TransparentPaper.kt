package aoc2021.day13


class TransparentPaper(dots: List<Dot> = emptyList()) {

    private var _dots = dots.toMutableList()

    val width: Int
        get() = (_dots.maxOfOrNull { it.x } ?: 0) + 1
    val height: Int
        get() = (_dots.maxOfOrNull { it.y } ?: 0) + 1

    val visibleDots: List<Dot>
        get() = _dots.toList()

    fun executeFold(foldInstruction: FoldInstruction) {
        when (foldInstruction.orientation) {
            FoldOrientation.HORIZONTAL -> executeHorizontalFold(foldInstruction.location)
            FoldOrientation.VERTICAL -> executeVerticalFoldAt(foldInstruction.location)
        }
    }

    private fun executeHorizontalFold(foldLocation: Int) {
        _dots.filter { it.x > foldLocation }
            .forEach { _dots.remove(it); _dots.add(it.copyAndMoveLeft(2 * (it.x - foldLocation))) }
        _dots = _dots.distinct().toMutableList()
    }

    private fun executeVerticalFoldAt(foldLocation: Int) {
        _dots.filter { it.y > foldLocation }
            .forEach { _dots.remove(it); _dots.add(it.copyAndMoveUp(2 * (it.y - foldLocation))) }
        _dots = _dots.distinct().toMutableList()
    }

    override fun toString(): String {
        var s = ""
        for (y in 0 until height) {
            for (x in 0 until width) {
                s += if (_dots.contains(Dot(x, y))) "#" else "."
            }
            s += "\n"
        }
        return s
    }

}