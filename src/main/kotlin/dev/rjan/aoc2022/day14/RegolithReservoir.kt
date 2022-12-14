package dev.rjan.aoc2022.day14

import dev.rjan.aoc2022.readInputFile
import kotlin.math.max
import kotlin.math.min


fun main() {
    print(part1())
    println()
    print(part2())
}

fun part2(): Any {
    TODO("Not yet implemented")
}

fun part1(): Any {
    val input = readInputFile("day14")
    val lines = input.split(System.lineSeparator())
    val coordinates =
        lines.map { it.split(" -> ") }.map { it.map { it.split(",") }.map { Position(it[0].toInt(), it[1].toInt()) } }
    val filledCoordinates = mutableSetOf<Position>()

    for (c in coordinates) {
        fillCoordinates(c, filledCoordinates)
    }
    val maxY = filledCoordinates.maxOf { it.y } + 2

    var counter = 0
    while (addSand(filledCoordinates, maxY)) {
        counter++
    }

    return counter
}

fun addSand(filledCoordinates: MutableSet<Position>, maxY: Int): Boolean {
    val current = Position(500, 0)

    while (current.y <= maxY) {
        if (!filledCoordinates.contains(Position(current.x, current.y + 1))) {
            current.y++
            continue
        }
        if (!filledCoordinates.contains(Position(current.x - 1, current.y + 1))) {
            current.x--
            current.y++
            continue
        }
        if (!filledCoordinates.contains(Position(current.x + 1, current.y + 1))) {
            current.x++
            current.y++
            continue
        }
        filledCoordinates.add(current)
        return true
    }
    return false
}

fun fillCoordinates(coordinates: List<Position>, filledCoordinates: MutableSet<Position>) {

    for (i in 1 until coordinates.size) {
        val a = coordinates[i - 1]
        val b = coordinates[i]
        if (a.x == b.x) {
            for (n in min(a.y, b.y)..max(a.y, b.y)) {
                filledCoordinates.add(Position(a.x, n))
            }
        } else if (a.y == b.y) {
            for (n in min(a.x, b.x)..max(a.x, b.x)) {
                filledCoordinates.add(Position(n, a.y))
            }
        } else {
            throw IllegalStateException()
        }
    }
}
