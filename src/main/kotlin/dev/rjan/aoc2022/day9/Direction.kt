package dev.rjan.aoc2022.day9

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun parse(value: String): Direction = when (value) {
            "U" -> UP
            "D" -> DOWN
            "L" -> LEFT
            "R" -> RIGHT
            else -> throw IllegalArgumentException()
        }
    }
}