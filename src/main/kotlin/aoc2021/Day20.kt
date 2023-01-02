package aoc2021

import utils.repeat


object Day20 {

    fun part1(filter: Filter, image: Image): Int {
        var enhancedImage = image
        repeat(2) { enhancedImage = filter.applyTo(enhancedImage) }
        return enhancedImage.numberOfLitPixels
    }

    fun part2(filter: Filter, image: Image): Int {
        var enhancedImage = image
        repeat(50) { enhancedImage = filter.applyTo(enhancedImage) }
        return enhancedImage.numberOfLitPixels
    }
}


class Image(val pixels: List<List<Char>>, val surrounding: Char = '.') {

    val height = pixels.size
    val width = pixels[0].size
    val numberOfLitPixels: Int by lazy {
        if (surrounding == '#') Int.MAX_VALUE
        else pixels.flatten().count { it == '#' }
    }

    fun getSubImage(centerX: Int, centerY: Int, sizeAroundCenter: Int): Image {
        return getSubImage(
            centerX - sizeAroundCenter,
            centerX + sizeAroundCenter,
            centerY - sizeAroundCenter,
            centerY + sizeAroundCenter
        )
    }

    fun getSubImage(fromX: Int, toX: Int, fromY: Int, toY: Int): Image {
        val newPixels = mutableListOf<List<Char>>()
        for (y in fromY..toY) {
            val row = mutableListOf<Char>()
            for (x in fromX..toX) {
                row += pixels[y][x]
            }
            newPixels += row
        }
        return Image(newPixels)
    }

    fun copyAndAddPadding(paddingSize: Int, paddingChar: Char): Image {
        val newPixels: MutableList<List<Char>> = mutableListOf()
        val emptyLine = paddingChar.repeat(width + 2 * paddingSize)
        repeat(paddingSize) { newPixels += emptyLine.toList() }
        for (oldLine in pixels) {
            val newLine = mutableListOf<Char>()
            repeat(paddingSize) { newLine += paddingChar }
            newLine += oldLine
            repeat(paddingSize) { newLine += paddingChar }
            newPixels += newLine
        }
        repeat(paddingSize) { newPixels += emptyLine.toList() }
        return Image(newPixels)
    }

    override fun toString(): String {
        var s = ""
        for (row in pixels) {
            for (pixel in row) {
                s += pixel
            }
            s += "\n"
        }
        return s
    }
}

class Filter(private val data: List<Char>, val size: Int = 1) {

    fun applyTo(image: Image): Image {
        val imageWithPadding = image.copyAndAddPadding(size * 2, image.surrounding)
        val newPixels = mutableListOf<List<Char>>()
        for (y in size until imageWithPadding.height - size) {
            val row = mutableListOf<Char>()
            for (x in size until imageWithPadding.width - size) {
                row += applyToPixel(imageWithPadding, x, y)
            }
            newPixels += row
        }
        return Image(newPixels, applyToSurrounding(image.surrounding))
    }

    private fun applyToPixel(image: Image, x: Int, y: Int): Char {
        val subImage = image.getSubImage(x, y, size)
        val subImageData = subImage.pixels.flatten().joinToString("")
        return getValueForImageData(subImageData)
    }

    private fun applyToSurrounding(surroundingChar: Char): Char {
        val filterAreaSize = size * 2 + 1
        val surroundingData = surroundingChar.repeat(filterAreaSize * filterAreaSize)
        return getValueForImageData(surroundingData)
    }

    private fun getValueForImageData(dataExcerpt: String): Char {
        val index = dataExcerpt.replace('.', '0').replace('#', '1').toInt(2)
        return data[index]
    }
}
