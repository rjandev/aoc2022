package dev.rjan.aoc2022.day2

enum class Outcome(val score: Int) {

    LOSE(0),
    DRAW(3),
    WIN(6);

    companion object {
        fun parse(value: String): Outcome = when (value) {
            "A", "X" -> LOSE
            "B", "Y" -> DRAW
            "C", "Z" -> WIN
            else -> throw IllegalArgumentException()
        }
    }
}