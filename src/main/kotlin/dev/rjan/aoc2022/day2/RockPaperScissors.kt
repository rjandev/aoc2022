package dev.rjan.aoc2022.day2

import dev.rjan.aoc2022.readInputFile
import kotlin.streams.toList

const val ROCK = 1
const val PAPER = 2
const val SCISSORS = 3

fun main() {
    print(calculateScore())
}

fun calculateScore() = readInputFile("day2")
    .split(System.lineSeparator())
    .parallelStream()
    .map { scoreRound(it) }
    .toList().sum()

fun scoreRound(input: String): Int {
    val opponentShape = input.split(" ")[0][0] - 64
    val myShape = input.split(" ")[1][0] - 23 - 64
    return myShape.code.plus(scoreOutcome(myShape.code, opponentShape.code))
}

fun scoreOutcome(myShape: Int, opponentShape: Int): Int {
    if (myShape == opponentShape) {
        return 3
    }
    if (ROCK == myShape) {
        return if (PAPER == opponentShape) 0 else 6
    }
    if (PAPER == myShape) {
        return if (SCISSORS == opponentShape) 0 else 6
    }
    if (SCISSORS == myShape) {
        return if (ROCK == opponentShape) 0 else 6
    }
    throw IllegalStateException()
}

