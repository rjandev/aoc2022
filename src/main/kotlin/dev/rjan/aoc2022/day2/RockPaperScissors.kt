package dev.rjan.aoc2022.day2

import dev.rjan.aoc2022.readInputFile
import kotlin.streams.toList

const val ROCK = 1
const val PAPER = 2
const val SCISSORS = 3

fun main() {
    print(calculateScore())
    println()
    print(part2())
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

fun part2() = readInputFile("day2")
    .split(System.lineSeparator())
    .parallelStream()
    .map { determineScore(it) }
    .toList().sum()

fun determineScore(input: String): Int {
    val opponentShape = input.split(" ")[0][0] - 64
    val expectedOutcome = input.split(" ")[1][0] - 23 - 64

    val myShape = determineShape(opponentShape.code, expectedOutcome.code)
    return myShape.plus(scoreOutcome(myShape, opponentShape.code))
}

fun determineShape(opponentShape: Int, expectedOutcome: Int): Int {
    if (expectedOutcome == 2) {
        return opponentShape
    }

    (1..3).forEach { shape ->
        when {
            scoreOutcome(shape, opponentShape) == 0 && expectedOutcome == 1 -> return shape
            scoreOutcome(shape, opponentShape) == 6 && expectedOutcome == 3 -> return shape
        }
    }
    throw java.lang.IllegalStateException()
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

