package dev.rjan.aoc2022.day2

import dev.rjan.aoc2022.day2.Outcome.DRAW
import dev.rjan.aoc2022.readInputFile
import kotlin.streams.toList

fun main() {
    print(part1())
    println()
    print(part2())
}

fun part1() = readInputFile("day2")
    .split(System.lineSeparator())
    .parallelStream()
    .map { splitLine(it) }
    .map { calculateScore(Shape.parse(it.first), Shape.parse(it.second)) }
    .toList().sum()

fun part2() = readInputFile("day2")
    .split(System.lineSeparator())
    .parallelStream()
    .map { splitLine(it) }
    .map { (left, right) -> Pair(Shape.parse(left), Outcome.parse(right)) }
    .map { (shape, outcome) -> Pair(shape, determineShape(shape, outcome)) }
    .map { calculateScore(it.first, it.second) }
    .toList().sum()

fun calculateScore(opponentShape: Shape, myShape: Shape): Int {
    return myShape.score.plus(myShape.determineOutcome(opponentShape).score)
}

private fun splitLine(input: String): Pair<String, String> {
    val split = input.split(" ")
    val opponentShape = split[0]
    val myShape = split[1]
    return Pair(opponentShape, myShape)
}


fun determineShape(opponentShape: Shape, expectedOutcome: Outcome): Shape {
    if (DRAW == expectedOutcome) {
        return opponentShape
    }

    (Shape.values()).forEach { shape ->
        if (expectedOutcome == shape.determineOutcome(opponentShape)) {
            return shape
        }
    }
    throw IllegalStateException()
}