package dev.rjan.aoc2022.day10

class Screen(val width: Int = 40, val height: Int = 6, val rows: MutableList<MutableList<String>> = mutableListOf()) {
    init {
        for (row in 0 until height) {
            rows.add(mutableListOf())
        }
    }

    var spritePosition = 1

    fun updateSpritePosition(newPosition: Int) {
        spritePosition = newPosition
    }

    fun drawPixel(cycle: Int) {
        val row = cycle / 40
        val pixel = when (spritePosition) {
            cycle - (row * 40) -> "#"
            cycle - (row * 40) + 1 -> "#"
            cycle - (row * 40) - 1 -> "#"
            else -> "."
        }
        rows[row].add(pixel)
    }

    fun render() {
        for (row in 0 until height) {
            println(rows[row])
        }
    }

}