package aoc2021.day13


object Day13 {

    fun part1(dots: List<Dot>, foldInstructions: List<FoldInstruction>): Int {

        val paper = TransparentPaper(dots)
        paper.executeFold(foldInstructions[0])
        return paper.visibleDots.size
    }

    fun part2(dots: List<Dot>, foldInstructions: List<FoldInstruction>): Int {
        val paper = TransparentPaper(dots)
        foldInstructions.forEach { paper.executeFold(it) }
        println(paper)
        return paper.visibleDots.size
    }

}
