package dev.rjan.aoc2022.day2

enum class Shape(val score: Int) {

    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun parse(value: String): Shape = when (value) {
            "A", "X" -> ROCK
            "B", "Y" -> PAPER
            "C", "Z" -> SCISSORS
            else -> throw IllegalArgumentException()
        }
    }

    fun determineOutcome(shape: Shape) = when (this) {
        shape -> Outcome.DRAW
        ROCK -> if (PAPER == shape) Outcome.LOSE else Outcome.WIN
        PAPER -> if (SCISSORS == shape) Outcome.LOSE else Outcome.WIN
        SCISSORS -> if (ROCK == shape) Outcome.LOSE else Outcome.WIN
    }
}